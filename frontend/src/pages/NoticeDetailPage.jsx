import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";


const NoticeDetailPage = () => {

  const { noticeId } = useParams();
  const [post, setPost] = useState([]);
  const [loading, setLoanding] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const apiUrl = import.meta.env.VITE_API_URL;

  const fetchPost = async (noticeId) => {
    setLoanding(true);
    setError(null);

    try {
      const response = await axios.get(
        `${apiUrl}/api/v1/notices/${noticeId}`,
        {
          withCredentials: true,
          headers: {
            Accept: "application/json",
          },
        }
      );

      setPost(response.data.data);
      
      console.log(response.data.data);

    } catch (error) {
      console.error("Error: ", error);
      setError("데이터를 불러오지 못했습니다.");
    } finally {
      setLoanding(false);
    }
  }

  useEffect(() => {
    if (noticeId) {
      fetchPost(noticeId);
    }
  }, [noticeId]);

  const goToNoticeList = () => {
    navigate('/notice');
  }

  return (
    <>
      <div className="w-full flex justify-start flex-col px-7 md:px-10 lg:px-14 py-30">
        <div className="w-full">
          <h1 className="text-3xl font-bold text-center">공지사항</h1>
          <span className="block text-center mt-5">행복 도서관 공지사항 페이지입니다.</span>
        </div>
        <div className="w-full">
          { loading && <p>불러오는 중...</p> }
          { error && <p style={{ color: "red" }}>{error}</p> }

          { !loading && !error && (
            <>
              <div className="flex flex-col items-center">
                <div className="w-full flex flex-col justify-start items-center mt-7 px-4">
                  <table className="table-auto border border-gray-300 w-full lg:w-[900px] table-auto">
                    <tbody>
                      <tr>
                        <th className="w-1/6 border border-gray-300 px-3 py-2 bg-gray-100">번호</th>
                        <td className="w-5/6 border border-gray-300 px-3 py-2">{post.id}</td>
                      </tr>
                      <tr>
                        <th className="w-1/6 border border-gray-300 px-3 py-2 bg-gray-100">작성일</th>
                        <td className="w-5/6 border border-gray-300 px-3 py-2">{post.createdDate.split('T').join(' ')}</td>
                      </tr>
                      <tr>
                        <th className="w-1/6 border border-gray-300 px-3 py-2 bg-gray-100">작성자</th>
                        <td className="w-5/6 border border-gray-300 px-3 py-2">{post.writer && post.writer.username}</td>
                      </tr>
                      <tr>
                        <th className="w-1/6 border border-gray-300 px-3 py-2 bg-gray-100">제목</th>
                        <td className="w-5/6 border border-gray-300 px-3 py-2 overflow-hidden wrap-anywhere">{post.title}</td>
                      </tr>
                      <tr className="h-[380px]">
                        <th className="w-1/6 border border-gray-300 px-3 py-2 bg-gray-100">내용</th>
                        <td className="w-5/6 border border-gray-300 px-3 py-2 whitespace-pre-line align-top overscroll-none">{post.content}</td>
                      </tr>
                    </tbody>
                  </table>
                </div>

                <div className="mt-8">
                  <button 
                    onClick={ goToNoticeList }
                    className="py-2 px-5 border border-teal-600 rounded-md hover:bg-teal-600 hover:text-white cursor-pointer"
                  >
                    목록
                  </button>
                </div>
              </div>
            </>
          )}
        </div>
      </div>
    </>
  );
}

export default NoticeDetailPage;