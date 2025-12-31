import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";


const BookListPage = () => {

  const [searchParams, setSearchParams] = useSearchParams();
  const [posts, setPosts] = useState([]);
  const [page, setPage] = useState(Number(searchParams.get('page')) || 0);
  const [size, setSize] = useState(Number(searchParams.get('size')) || 10);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [type, setType] = useState('ALL');
  const [keyword, setKeyword] = useState(searchParams.get("keyword") || "");
  const navigate = useNavigate();

  const apiUrl = import.meta.env.VITE_API_URL;

  useEffect(() => {
    const newPage = Number(searchParams.get('page')) || 0;
    const newSize = Number(searchParams.get('size')) || 10;
    setPage(newPage);
    setSize(newSize); 
  }, [searchParams]);

  useEffect(() => {
    fetchBooks(page, size);
  }, [page, size]);

  // 전체목록 api
  const fetchBooks = async (page, size) => {
    setLoading(true);
    setError(null);

    try {
      let response;

      if (type && keyword) {
        // 검색 조건이 있을 경우 api
        response = await axios.get(
          `${apiUrl}/api/v1/books/search`,
          {
            params: {
              type: type,
              keyword: keyword,
              page: page,
              size: size,
            },
            withCredentials: true,
            headers: {
              Accept: "application/json",
            }
          },
        );

      } else {
        // 전체 목록 조회
        response = await axios.get(
          `${apiUrl}/api/v1/books`,
          {
            params: {
              page: page,
              size: size,
            },
            withCredentials: true,
            headers: {
              Accept: "application/json",
            }
          },
        );
      }

      console.log(response.data);
      setPosts(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
    } catch (error) {
      console.log(error);
      setError("Error: ", error);
    } finally {
      setLoading(false);
    }
  }

  // 상세페이지 이동
  const goToDetailPage = (id) => {
    if (keyword != null) {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', keyword);
      sessionStorage.setItem('type', type);
    } else {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', null);
      sessionStorage.setItem('type', null);
    }
    navigate(`/books/${id}`);
  }

  // 페이지 이동
  const handlePageClick = (pageNumber) => {
    if (keyword != null) {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', keyword);
      sessionStorage.setItem('type', type);
    } else {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', null);
      sessionStorage.setItem('type', null);
    }
    setPage(pageNumber);
  }

  // 이전 페이지
  const handlePrev = () => {
    
    if (keyword != null) {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', keyword);
      sessionStorage.setItem('type', type);
    } else {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', null);
      sessionStorage.setItem('type', null);
    }

    if (page > 0) {
      setPage(page - 1);
      fetchBooks(currentFilter, page - 1);
    }
  }

  // 다음 페이지
  const handleNext = () => {

    if (keyword != null) {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', keyword);
      sessionStorage.setItem('type', type);
    } else {
      sessionStorage.setItem('lastPage', page);
      sessionStorage.setItem('size', size);
      sessionStorage.setItem('keyword', null);
      sessionStorage.setItem('type', null);
    }

    if (page < totalPages - 1) {
      setPage(page + 1);
      fetchBooks(currentFilter, page + 1);
    }
  }

  // 도서 검색
  const searchBook = async () => {
    console.log(keyword);
    console.log(type);
    console.log(page);
    setPage(0);
    
    try {
      const response = await axios.get(
        `${apiUrl}/api/v1/books/search`,
        {
          params: {
            type: type,
            keyword: keyword,
            page: page,
            size: size,
          },
          withCredentials: true,
          headers: {
            Accept: "application/json",
          }
        }
      );

      // console.log(response.data.data);
      setPosts(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
    } catch (error) {
      console.log("Error: ", error.response.data.data);
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
  // console.log("keyword: ", keyword);
  // console.log("type: ", type);

  console.log(posts);
  let content;
  if (!loading && !error) {
    if (posts && posts.length > 0) {
      content = (
        <>
          {posts.map((post) => (
            <div 
              key={post.id}
              onClick={ () => goToDetailPage(post.id) }
              className="
                w-full h-auto max-h-[300px] 
                flex flex-row p-4
                border border-gray-400 rounded-md
                hover:border-2
                hover:border-teal-600
                hover:shadow-xl
                bg-white overflow-hidden"
            >
              <div className="w-full h-full flex sm:flex-1 flex-2 overflow-hidden justify-end ">
                <img src="https://placehold.co/600x800" alt="" className="h-full object-contain object-top object-right" />
              </div>
              <div className="w-full flex sm:flex-row flex-col justify-between items-start md:flex-4 sm:flex-3 flex-4 lg:text-[18px]  sm:text-[16px] text-[15px]">
                <div className="w-full flex flex-4 h-full flex-col sm:p-1 p-0 sm:px-4 px-5 leading-6 sm:leading-8">
                  <span className="lg:text-[25px] md:text-[23px] sm:text-[20px] text-[18px] mb-1">{post.title}</span>
                  <span>{post.author} | {post.publisher} | {post.publishedDate}</span>
                  <span>위치: {post.location}</span>
                </div>
                <div className="w-full h-full flex flex-2 sm:justify-center justify-start pl-4">
                  <span className={
                    `border px-3 py-2 sm:self-center self-end lg:text-[20px] md:text-[18px] sm:text-[16px] text-[15px]
                    ${post.status != 'AVAILABLE' ? "border-red-700 text-red-700" : "border-teal-700 text-teal-700"}
                    `
                  }
                    
                  >
                    { post.status != 'AVAILABLE' ? '대출불가' : '대출가능' }
                  </span>
                </div>
              </div>
            </div>
          ))}
        </>
      );
    } else {
      content = (
        <>
          <div className="my-15 text-center">등록된 도서가 없습니다.</div>
        </>
      );
    }
  }

  return (
    <>
      <div>
        <div className="w-full flex justify-start flex-col px-7 md:px-10 lg:px-14 py-30">
          <div className="w-full">
            <h1 className="text-3xl font-bold text-center">도서 목록</h1>
            <span className="block text-center mt-5">도서 검색을 할 수 있는 페이지입니다.</span>
          </div>

          {/* 검색 */}
          <div className="w-full flex justify-center mt-10">
            <div className="w-full sm:w-120">
              <div className="flex">
                <div 
                  className="
                    flex justify-center 
                    w-2/10 min-w-[100px] mr-1 pr-1 py-1
                    border rounded-sm border-gray-300 
                    text-sm
                    overflow-hidden
                  ">
                  <select 
                    name="type" id="type"
                    value={ type }
                    onChange={ (e) => setType(e.target.value) }
                    className="outline-none pl-2 pr-1 overflow-hidden"
                  >
                    <option value="ALL">통합검색</option>
                    <option value="TITLE">도서명</option>
                    <option value="AUTHOR">저자</option>
                  </select>
                </div>
                <input 
                  type="text" 
                  placeholder="검색어를 입력해주세요"
                  value={ keyword }
                  onChange={ (e) => setKeyword(e.target.value) }
                  className="
                    w-6/10
                    py-3 px-3
                    border rounded-l-sm border-gray-300
                    outline-none 
                    text-sm leading-5 
                    bg-white"
                />
                <button 
                  onClick={ searchBook }
                  className="
                    rounded-r-sm bg-teal-600 
                    hover:bg-teal-700 text-white 
                    px-4 w-2/10 cursor-pointer
                  ">검색</button>
              </div>
            </div>
          </div>


          {/* 페이징 정보 */}
          <div className="w-full flex justify-center mt-5">
            <div className="flex justify-between w-full lg:w-[900px]">
              <div className="flex flex-row text-[14px] leading-7">
                <div className="text-gray-700">총 { posts.length > 0 ? totalElements : 0 }건</div>
                <div>&nbsp;&nbsp;|&nbsp;&nbsp;</div>
                <div className="text-gray-700">
                  <span>{ posts.length > 0 ? page+1 : page }</span>
                  /{ posts.length > 0 ? totalPages : 0 } 페이지
                </div>
              </div>
            </div>
          </div>

          {/* 도서목록 */}
          {loading && <p style={{textAlign: 'center', marginTop: '100px'}}>불러오는 중...</p>}
          {error && (<p style={{textAlign: 'center', marginTop: '100px', color: 'red'}}>{error.message || String(error)}</p>)}

          <div className="w-full flex flex-col justify-start items-center mt-2">
            <div className="w-full grid gap-4 h-auto lg:w-[900px]">
              {/* 아이템's */}
              { content }

              {/* 페이징 버튼 */}
              <div className="mt-5 text-center">
                <button
                  onClick={ handlePrev }
                  disabled={page === 0 || totalPages === 0}
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
                  disabled={page === totalPages - 1 || totalPages === 0}
                  className="px-3 py-1 cursor-pointer disabled:opacity-50"
                >이후</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );

}

export default BookListPage;