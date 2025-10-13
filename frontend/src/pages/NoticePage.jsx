import axios from "axios";
import React, { useEffect, useState } from "react";

const NoticePage = () => {

  const[posts, setPosts] = useState([]);
  const[page, setPage] = useState(0);
  const size = 15;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [totalPages, setTotalPages] = useState(0);

  const fetchNotice = async (page) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/notices",
        {
          param: {
            page: page,
            size: size,
          },
          withCredentials: true,
          headers: {
            Accept: "application/json",
          },
        }
      );

      setPosts(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);

      // 값 확인
      console.log(response.data.data.content);
    } catch (error) {
      console.log("Error: ", error);
      setError(error);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    fetchNotice(page);
  }, [page]);

  return (
    <>
      <div className="w-full border">
        <div className="w-full flex flex-col px-7 md:px-10 lg:px-14 py-30 border">
          <div className="w-full border">
            <h1 className="text-3xl font-bold text-center">공지사항</h1>
            <span className="block text-center mt-5">행복 도서관 공지사항 페이지입니다.</span>
          </div>
          <div className="w-full">
            {loading && <p>불러오는 중...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}

            {!loading && !error && (
              <div className="w-full flex flex-col justify-center items-center mt-7 px-4">
                <table className="border border-gray-300 w-full lg:w-[900px]">
                  <thead>
                    <tr className="bg-gray-100">
                      <th className="border border-gray-300 p-2">번호</th>
                      <th className="border border-gray-300 p-2">제목</th>
                      <th className="border border-gray-300 p-2">작성자</th>
                      <th className="border border-gray-300 p-2">작성일</th>
                    </tr>
                  </thead>
                  <tbody>
                    {posts.map((post) => (
                      <tr>
                        <td className="border border-gray-300 text-center p-2 truncate sm:w-1/12">{post.id}</td>
                        <td className="border border-gray-300 p-2 truncate sm:w-6/12">{post.title}</td>
                        <td className="border border-gray-300 text-center p-2 truncate sm:w-1/12">{post.writer.username}</td>
                        <td className="border border-gray-300 text-center p-2 truncate sm:w-1/12">{post.createdDate.split('T')[0]}</td>
                      </tr>
                    ))}
                  </tbody>
                </table>

                <div className="mt-5 text-center">
                  <button className="px-3 py-1 cursor-pointer">이전</button>
                  <button
                    className="px-[10px] py-[6px] mx-[3px] text-base cursor-pointer"
                  >1</button>
                  <button className="px-3 py-1 cursor-pointer">다음</button>
                </div>
              </div>

            )}
            
          </div>
        </div>
      </div>
    </>
  );
}

export default NoticePage;