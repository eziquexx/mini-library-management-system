import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

const HomePage = () => {

  const [searchParams, setSearchParams] = useSearchParams();
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(Number(searchParams.get('page')) || 0);
  const [size, setSize] = useState(Number(searchParams.get('size')) || 10);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [totalPages, setTotalPages] = useState(0);
  const navigator = useNavigate();

  useEffect(() => {
    const newPage = Number(searchParams.get('page')) || 0;
    const newSize = Number(searchParams.get('size')) || 10;
    setPage(newPage);
    setSize(newSize); 
  }, [searchParams]);

  useEffect(() => {
    fetchPosts(page, size);
  }, [page, size]);

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
    sessionStorage.setItem('lastPage', page);
    sessionStorage.setItem('size', size);
    navigator(`/books/${id}`);
  }

  return (
    <>
      <div className="w-full">
        <div className="w-full flex justify-center px-7 md:px-10 lg:px-14 py-30">
          <div className="w-7xl max-w-7xl flex flex-col overflow-hidden">
            {/* 타이틀 */}
            <div className="w-full flex flex-col">
              <h1 className="text-3xl font-bold text-center">행복 도서관</h1>
              <span className="text-center mt-5">행복 도서관에 오신 것을 환영합니다.</span>
            </div>

            {loading && <p>불러오는 중...</p>}
            {error && <p style={{ color: "red"}}>{error}</p>}

            {/* 도서 아이템 */}
            {!loading && !error && (
              <>
                <div className="flex flex-col items-center w-full overflow-hidden">
                  <div className="mt-20 max-w-[1100px]">
                    <h2 className="text-3xl mb-4">인기 도서</h2>
                    <ul className="
                    grid items-start w-full h-auto
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
                  </div>

                  <div className="mt-20 max-w-[1100px]">
                    <h2 className="text-3xl mb-4">신작 도서</h2>
                    <ul className="
                    grid items-start w-full h-auto
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
                  </div>

                  {/* <div className="mt-5 text-center">
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
                  </div> */}
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