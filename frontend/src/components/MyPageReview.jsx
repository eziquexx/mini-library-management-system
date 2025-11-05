import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import useUserStore from "../stores/useUserStore";
import axios from "axios";
import MyPageReviewModal from "./MyPageReviewModal";


const MyPageReview = () => {

  const {user, logout} = useUserStore();
  const [searchParams, setSearchParams] = useSearchParams();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(Number(searchParams.get('page')) || 0);
  const [size, setSize] = useState(Number(searchParams.get('size')) || 10);
  const [data, setData] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const [selectedReviewId, setSelectedReviewId] = useState(null);
  const [keyword, setKeyword] = useState("");


  useEffect (() => {
    fetchBookReview(page, size);
  }, [page, size])
  

  // 리뷰 전체 api
  const fetchBookReview = async (page, size) => {

    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/user/me/reviews`,
        {
          params: {
            page: page,
            size: size,
          },
          withCredentials: true,
          headers: {
            Accept: "application/json",
          }
        }
      );

      console.log(response.data.data);
      setData(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
    } catch (error) {
      console.log("Error: ", error.response);
      setError(error.response);
    } finally {
      console.log("리뷰 전체 목록 조회 완료");
      setLoading(false);
    }
  }

  // 리뷰 검색 api
  const fetchReviewSearch = async (keyword) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/user/me/reviews/search`,
        {
          params: {
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

      console.log("검색결과: ", response.data.data);
      setData(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
    } catch (error) {
      console.log("Error: ", error.response);
      setError(error.response);
      setTotalElements(0);
    } finally {
      console.log("리뷰 검색 완료");
    }
  }

  // 이전 페이지
  const handlePrev = () => {
    if (page > 0) {
      setPage(page - 1);
      fetchBookReview(currentFilter, page - 1);
    }
  }

  // 다음 페이지
  const handleNext = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
      fetchBookReview(currentFilter, page + 1);
    }
  }

  // 페이지 번호
  const handlePageClick = (pageNumber) => {
    setPage(pageNumber);
  }

  // 모달창
  const handleOpenDialog = (reviewId) => {
    document.getElementById(`dialogDetailReview${reviewId}`).showModal();
  }

  // 검색
  const handleSearch = () => {
    fetchReviewSearch(keyword);
  }
  
  // 리뷰 목록
  let content;
  if (!loading && !error) {
    if (data && data.length > 0) {
      content = (
        <>
          {data.map((review) => (
            <div 
              key={review.id}
              // command="show-modal" 
              // commandfor="dialogtest"
              onClick={() => handleOpenDialog(review.id)}
              className="flex flex-col w-full"
            >
              <div className="
                flex flex-row w-full overflow-hidden p-2 
                border-1 border-transparent 
                hover:border-teal-600 
                cursor-pointer"
              >
                {/* 썸네일 */}
                <div className="flex w-[100px] min-w-[100px] items-start shrink-0">
                  <img src="https://placehold.co/420x600" alt="" className="w-auto block h-auto" />
                </div>
                {/* 텍스트 */}
                <div className="flex flex-col justify-start self-start w-full min-w-0 leading-6 text-[15px] ml-2 text-gray-700">
                  <div className="font-bold text-black">{review.bookTitle}</div>
                  <div>작성일: {review.createdDate.split('T').join(" ")}</div>
                  <div className="flex flex-row w-full mt-2 leading-6 overflow-hidden items-center">
                    <div className="w-full min-w-0 px-2 py-1 h-[82px] border border-gray-200 rounded line-clamp-3">
                      {review.content}
                    </div>
                  </div>
                </div>
              </div>
              <div className="w-full border-b-1 border-gray-200"></div>
              <MyPageReviewModal 
                id={review.id} 
                reviewId={review.id}
                fetchBookReview={fetchBookReview}
                mode="detailReview"
              />
            </div>
          ))}
        </>
      );
    } else {
      content = (
        <>
          <div className="my-15 text-center">등록된 리뷰가 없습니다.</div>
        </>
      );
    }
  }

  return (
    <>
      <div className="flex flex-col border border-gray-300 mt-2 p-8 leading-10">
        <div className="text-2xl mb-4">리뷰내역 정보</div>
        {/* 검색 영역 */}
        <div className="w-full bg-gray-100 px-3 py-4 mb-4 flex flex-row justify-center items-center">
          <div className="w-full flex flex-row justify-center">
            <input 
              type="text" 
              className="
                w-9/12
                sm:w-6/12
                text-sm px-3 py-3 leading-none
                bg-white
                border border-gray-300 rounded-sm outline-none
                focus:border-teal-600"
              placeholder="검색어를 입력해주세요."
              value={keyword}
              onChange={(e) => setKeyword(e.target.value)}
            />
            <button 
              className="
                w-3/12
                sm:w-2/12
                px-5 py-2 ml-2 
                text-white leading-none
                bg-teal-600 rounded-sm
                hover:bg-teal-700
                cursor-pointer"
              onClick={handleSearch}  
            >검색</button>
          </div>
        </div>
        <div className="flex flex-col w-full items-center">
          <div className="flex flex-col w-full justify-center items-center">
            {/* 총 개수와 페이지 */}
            <div className="self-start flex flex-row text-sm mb-1">
              <div>총 {data.length > 0 ? totalElements : 0}건</div> 
              <div className="mx-2">|</div>
              <div>{ data.length > 0 ? page + 1 : page }
                  /{ data.length > 0 ? totalPages : 0 }페이지</div>
            </div>

            {/* 리뷰 내역 items */}
            {content}

            {/* 페이징 */}
            <div className="mt-2">
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
                >{i + 1}</button>
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
    </>
  );
}

export default MyPageReview;