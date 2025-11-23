import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const NoticePage = () => {

  const[posts, setPosts] = useState([]);
  const[page, setPage] = useState(0);
  const size = 15;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [keyword, setKeyword] = useState('');
  const navigate = useNavigate();

  const apiUrl = import.meta.env.VITE_API_URL;

  // 공지 전체 목록 조회
  const fetchNotice = async (page) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        `${apiUrl}/api/v1/notices`,
        {
          params: {
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
      setTotalElements(response.data.data.totalElements || 1);

      // 값 확인
      console.log(response.data.data);
    } catch (error) {
      console.log("Error: ", error);
      setError(error);
    } finally {
      setLoading(false);
    }
  }

  // 렌디링 시 공지 전체 목록 조회 
  useEffect(() => {
    fetchNotice(page);
  }, [page]);

  // 페이징 기능
  const handleNext = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
      fetchData(currentFilter, page + 1);
    }
  }
  const handlePrev = () => {
    if (page > 0) {
      setPage(page - 1);
      fetchData(currentFilter, page - 1);
    }
  } 
  const handlePageClick = (pageNumber) => {
    setPage(pageNumber);
  }

  // 상세페이지 이동
  const goToDetailPage = (id) => {
    console.log(id);
    navigate(`/notice/${id}`);
  }

  // 검색 기능
  const searchNotice = async () => {
    console.log(keyword)
    try {
      const response = await axios.get(
        `${apiUrl}/api/v1/notices/search`,
        {
          params: {
            keyword: keyword,
            page: page,
            size: size
          },
          withCredentials: true,
          headers: {
            Accept: "application/json",
          }
        }
      );

      console.log(response.data.data);
      
      setPosts(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
    } catch (error) {
      console.log("Error Data: ", error.response.data.data);
      setError(error.response.data.data);
      setPosts(error.response.data.data);
      setPage(0);
      setTotalPages(0);
      setTotalElements(0);
    } finally {
      setLoading(false);
    }
  }

  // 값 확인용
  console.log("page: ", page);
  console.log("size: ", size);
  console.log("totalPages: ", totalPages);
  console.log("totalElements: ", totalElements);

  // 공지목록 조회시 tbody부분
  let content;
  if (!loading && !error) {
    if (posts && posts.length > 0) {
      content = (
        <tbody>
          {posts.map((post) => (
            <tr key={post.id}>
              <td className="border border-gray-300 text-center p-2 truncate sm:w-1/12">{post.id}</td>
              <td className="border border-gray-300 p-2 truncate sm:w-6/12">
                <button
                  onClick={ () => goToDetailPage(post.id) }
                  className="cursor-pointer"
                >
                  {post.title}
                </button>
              </td>
              <td className="border border-gray-300 text-center p-2 truncate sm:w-1/12">{post.writer.username}</td>
              <td className="border border-gray-300 text-center p-2 truncate sm:w-1/12">{post.createdDate.split('T')[0]}</td>
            </tr>
          ))}
        </tbody>
      );
    } else {
      content = (
        <tbody>
          <tr>
            <td colSpan={4} className="text-center py-10 text-gray-500">등록된 게시물이 없습니다.</td>
          </tr>
        </tbody>
      )
    }
  }

  return (
    <>
      <div className="">
        <div className="w-full flex justify-start flex-col px-7 md:px-10 lg:px-14 py-30">
          <div className="w-full">
            <h1 className="text-3xl font-bold text-center">공지사항</h1>
            <span className="block text-center mt-5">행복 도서관 공지사항 페이지입니다.</span>
          </div>
          {/* 검색 */}
          <div className="w-full flex justify-center mt-14">
            <div className="flex justify-between w-full lg:w-[900px]">
              <div className="flex flex-row text-[14px] leading-7">
                <div className="text-gray-700">총 { totalElements }건</div>
                <div>&nbsp;&nbsp;|&nbsp;&nbsp;</div>
                <div className="text-gray-700">
                  <span>{ posts ? page+1 : page }</span>
                  /{ totalPages } 페이지
                </div>
              </div>
              <div>
                <input 
                  type="text"
                  placeholder="검색어를 입력해주세요" 
                  value={ keyword }
                  onChange={ (e) => setKeyword(e.target.value) }
                  className="
                    py-1 px-2
                    w-[160px]
                    sm:w-[200px]
                    border rounded-l-sm border-gray-300
                    text-[12px] leading-5
                    bg-white
                  "
                />
                <button
                  onClick={ searchNotice }
                  className="
                    py-1 px-2
                    ml-[-1px]
                    border
                    rounded-r-sm
                    border-teal-600
                    cursor-pointer 
                    text-[12px] leading-5
                    text-white
                    bg-teal-600
                    hover:bg-teal-700
                    hover:border-teal-700
                  "
                >검색</button>
              </div>
            </div>
          </div>
          {/* 공지목록 */}
          <div className="w-full">
            {loading && <p>불러오는 중...</p>}
            {error && <p style={{ color: "red" }}>{error}</p>}
            
            <div className="w-full flex flex-col justify-start items-center mt-2">
              <div className="min-h-[240px] w-full lg:w-[900px]">
                <table className="border border-gray-300 w-full table-auto">
                  <thead>
                    <tr className="bg-gray-100">
                      <th className="max-w-[98px] w-1/8 border border-gray-300 p-2">번호</th>
                      <th className="max-w-[582px] w-5/8 border border-gray-300 p-2">제목</th>
                      <th className="max-w-[98px] w-1/8 border border-gray-300 p-2">작성자</th>
                      <th className="max-w-[122px] w-1/8 border border-gray-300 p-2">작성일</th>
                    </tr>
                  </thead>
                  {/* 공지목록 tbody부분 */}
                  { content }
                </table>
              </div>

              <div className="mt-5 text-center">
                <button 
                  onClick={ handlePrev}
                  disabled={ page === 0 || totalPages === 0 }
                  className="px-3 py-1 cursor-pointer disabled:opacity-50"
                >이전</button>
                {[...Array(totalPages)].map((_, i) => (
                  <button
                    key={i}
                    onClick={() => handlePageClick(i)}
                    className={`px-[10px] py-[6px] mx-[3px] text-base cursor-pointer ${
                      i === page ? "text-teal-600 font-bold" : ""
                    }`}
                  >
                    {i + 1}
                  </button>
                ))}
                <button
                  onClick={ handleNext }
                  disabled={ page === totalPages - 1 || totalPages === 0 }
                  className="px-3 py-1 cursor-pointer disabled:opacity-50"
                >다음</button>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </>
  );
}

export default NoticePage;