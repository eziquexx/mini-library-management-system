import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const HomePage = () => {

  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(0);
  const size = 10;
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [totalPages, setTotalPages] = useState(0);
  const navigator = useNavigate();

  const fetchPosts = async (page) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/books",
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

      console.log(response.data.data.content);
    } catch (error) {
      console.error("Error fetching posts:", error);
      setError("데이터를 불러오지 못했습니다.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchPosts(page);
  }, [page]);

  const handleNext = () => {
    setPage((prev) => prev + 1);
  }

  const handlePrev = () => {
    if (page > 0) setPage((prev) => prev -1);
  }

  const handlePageClick = (pageNumber) => {
    setPage(pageNumber);
  }

  // 상세페이지 이동
  const goToDetailPage = (id) => {
    console.log(id);
    navigator(`/books/${id}`);
  }

  return (
    <>
      <div className="w-full">
        <div className="w-full flex justify-center px-7 md:px-10 lg:px-14 py-30">
          <div className="w-7xl max-w-7xl flex flex-col overflow-hidden">
            <div className="w-full flex flex-col">
              <h1 className="text-3xl font-bold text-center">행복 도서관</h1>
              <span className="text-center mt-5">행복 도서관에 오신 것을 환영합니다.</span>
            </div>
            <div className="w-full flex justify-center mt-6">
              <form className="w-full md:w-120">
                <div className="flex">
                  <input 
                    type="text" 
                    placeholder="검색어를 입력해주세요"
                    className="
                      w-5/6
                      py-4 px-5
                      border rounded-l-sm border-gray-300 
                      text-sm leading-5 
                      bg-white"
                  />
                  <button className="rounded-r-sm bg-teal-600 hover:bg-teal-700 text-white px-4 w-1/6 cursor-pointer">검색</button>
                </div>
              </form>
            </div>

            {loading && <p>불러오는 중...</p>}
            {error && <p style={{ color: "red"}}>{error}</p>}

            {!loading && !error && (
              <>
                <div className="mt-7 overflow-hidden">
                  <ul className="
                  grid items-start min-h-[600px] 
                  lg:grid-cols-5 lg:gap-4 
                  md:grid-cols-4 md:gap-4 
                  sm:grid-cols-3 sm:gap-4 
                  grid-cols-2 gap-4 ">
                    {posts.map((post) => (
                      <li 
                        key={[post.id]}
                        onClick={ () => goToDetailPage(post.id) }
                        className="
                          border 
                          border-zinc-300
                          rounded-md
                          shadow-sm
                          max-w-xl
                          overflow-hidden
                        "
                      >
                        <div className="max-w-xl mx-auto bg-teal-300 text-center">
                          <img src="https://placehold.co/800x900" alt="" className="w-full h-auto" />
                        </div>
                        <div className="w-full p-3 text-sm/6 tracking-tight overflow-hidden wrap-anywhere">
                          <p className="truncate font-bold">{post.title}</p>
                          <p className="truncate">{post.author}</p>
                          <p className="truncate">{post.publisher}</p>
                          <p className="truncate">{post.publishedDate}</p>
                        </div>
                      </li>
                    ))}
                  </ul>

                  <div className="mt-5 text-center">
                    <button 
                      onClick={ handlePrev }
                      disabled={page === 0}
                      className="px-3 py-1 cursor-pointer disabled:opacity-50"
                    >
                      이전
                    </button>
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
                      disabled={ page === totalPages - 1 }
                      className="px-3 py-1 cursor-pointer disabled:opacity-50"
                    >
                      다음
                    </button>
                  </div>
                </div>              
              </>
            )}
          </div>
        </div>
      </div>
    </>
  );
}

export default HomePage;