import { useEffect, useRef, useState } from "react";
import useUserStore from "../stores/useUserStore";
import axios from "axios";
import { useSearchParams } from "react-router-dom";
import MyPageLoanModal from "./MyPageLoanModal";
import MyPageReviewModal from "./MyPageReviewModal";


const MyPageLoan = () => {

  const {user, logout} = useUserStore();
  const [searchParams, setSearchParams] = useSearchParams();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(Number(searchParams.get('page')) || 0);
  const [size, setSize] = useState(Number(searchParams.get('size')) || 5);
  const [data, setData] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [selectLoanId, setSelectLoanId] = useState(null);
  const [totalElements, setTotalElements] = useState(0);
  const [keyword, setKeyword] = useState("");

  console.log(user);

  useEffect(() => {
    fetchBookLoans(page, size);
  }, [page, size])

  // 도서 대출 내역 전체 조회 api
  const fetchBookLoans = async (page, size) => {

    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/user/me/loans",
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
      setLoading(false);
    }
  };

  // 대출 검색 api
  const fetchLoanSearch = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/user/me/loans/search`,
        {
          params: {
            keyword: keyword,
            page: page,
            size: size
          },
          withCredentials: true,
          headers: {
            Accept: "application/json"
          }
        }
      );

      console.log("검색결과: ", response.data.data);
      setData(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);

    } catch (error) {
      console.log("Error: ", error.response);
      setError(error.response);
    } finally {
      console.log("대출 검색 종료");
    }
  }

  // 페이징 - 이전 버튼
  const handlePrev = () => {
    if (page > 0) {
      setPage(page - 1);
      fetchBookLoans(currentFilter, page - 1);
    }
  }

  // 페이징 - 다음 버튼
  const handleNext = () => {
    if (page < totalPages - 1) {
      setPage(page + 1);
      fetchBookLoans(currentFilter, page + 1);
    }
  }

  // 페이징 - 번호
  const handlePageClick = (pageNumber) => {
    setPage(pageNumber);
  }
  
  // 도서 대출 상태 체크
  let bookStatus = (status) => {
    switch(status) {
      case "LOANED":
        return "대출중";
      case "RETURNED":
        return "반납 완료";
      case "OVERDUE":
        return "연체됨";
      case "LOST":
        return "분실됨";
      default:
        return "";
    }
  };

  // 리뷰 모달
  const handleClick = (mode, bookId, reviewId) => {
    let modalId;
    // 모드별 코드 실행
    if (mode === "createReview") {
      modalId = `dialogCreateReview${bookId}`;
      // document.getElementById(`dialogCreateReview${bookId}`).showModal();
    } else if (mode === "updateReview") {
      modalId = `dialogUpdateReview${reviewId}`;
      // document.getElementById(`dialogUpdateReview${reviewId}`).showModal();
    }

    const modal = document.getElementById(modalId);
    modal.showModal();
  }

  // 검색
  const handleSearch = () => {
    fetchLoanSearch(keyword);
  }

  // 도서 대출 내역 있는 경우와 없는 경우 데이터 처리
  let content;
  if (!loading && !error) {
    if (data && data.length > 0) {
      content = (
        <>
          <div className="flex flex-col w-full items-center">
            <div className="flex flex-col w-full">
              {/* 대출 내역 items */}
              {data.map((item) => (
                <div 
                  key={item.id}
                  className="flex flex-col w-full border border-gray-200 p-3 mb-2"
                >
                  <div className="flex flex-row w-full">
                    <div className="flex w-[120px] min-w-[120px] items-start">
                      <img src="https://placehold.co/420x600" alt="" className="w-auto block h-auto" />
                    </div>
                    <div className="flex flex-col justify-start self-start leading-6 text-[15px] ml-2 text-gray-700">
                      <div className="font-bold text-black">{item.bookTitle}</div>
                      <div className="flex sm:flex-row flex-col">
                        <div>{item.author}</div>
                        <div className="mx-2 hidden sm:block">|</div>
                        <div>{item.publisher}</div>
                      </div>
                      <div>위치:<span className="ml-[4px]">{item.location}</span></div>
                      <div className="font-bold">상태: 
                        <span className="text-blue-700 ml-[4px] font-normal">
                          { bookStatus(item.status) }
                        </span>
                      </div>
                      <div className="flex sm:flex-row flex-col">
                        <div className="font-bold">대출일:<span className="ml-[4px] font-normal">{item.loanDate.split('T', 1)}</span></div>
                        <div className="mx-2 hidden sm:block">|</div>
                        {item.returnDate
                          ? (
                              <div className="font-bold">
                                반납일:
                                <span className="text-red-700 ml-[4px] font-normal">{item.returnDate.split('T', 1)}</span>
                              </div>
                            )
                          : (
                              <div className="font-bold">
                                반납예정일:
                                <span className="text-red-700 ml-[4px] font-normal">{item.dueDate.split('T', 1)}</span>
                              </div>
                            )
                          }
                        
                      </div>
                      <div>대출자:<span className="ml-[4px]">{item.borrower}</span></div>
                      <div className="flex flex-row items-center">
                        리뷰작성:
                        {item.reviewWritten != true 
                          ? (
                              <>
                                <span className="text-green-700 ml-[4px]">미작성</span>
                                <div className="mx-2">|</div>
                                <button 
                                  id="createReview" 
                                  className="cursor-pointer hover:underline"
                                  onClick={() => handleClick("createReview", item.bookId, item.reviewId)}
                                >작성하기&nbsp;&gt;</button>
                                
                                <MyPageReviewModal 
                                  id={item.id} 
                                  mode="createReview"
                                  bookId={item.bookId}
                                  reviewId={item.reviewId}
                                  keyword={keyword}
                                  fetchBookLoans={fetchBookLoans}
                                  fetchLoanSearch={fetchLoanSearch}
                                  // fetchBookReview={fetchBookReview}
                                />
                              </>
                            )
                          : (
                              <>
                                <span className="text-green-700 ml-[4px]">작성</span>
                                <div className="mx-2">|</div>
                                <button 
                                  id="updateReview" 
                                  className="cursor-pointer hover:underline"
                                  onClick={() => handleClick("updateReview", item.bookId, item.reviewId)}
                                >수정하기&nbsp;&gt;</button>
                              
                                <MyPageReviewModal 
                                  id={item.id} 
                                  mode="updateReview"
                                  bookId={item.bookId}
                                  reviewId={item.reviewId}
                                  keyword={keyword}
                                  fetchBookLoans={fetchBookLoans}
                                  fetchLoanSearch={fetchLoanSearch}
                                  // fetchBookReview={fetchBookReview}
                                />
                              </>
                            )
                        }
                      </div>
                      <div>대출연장:<span className="ml-[4px]">{item.extended != true ? "0" : "1"}</span>회</div>
                      <button 
                        command="show-modal" 
                        commandfor="dialogExtended"
                        onClick={() => {
                          setSelectLoanId(item.id);
                        }}
                        disabled={item.extended != false}
                        className="
                          mt-2 px-2 py-1 
                          border border-gray-500 
                          text-[15px] self-start
                          hover:border-teal-600
                          hover:bg-teal-600
                          hover:text-white
                          disabled:border-gray-400
                          disabled:text-gray-400
                          disabled:hover:bg-transparent"
                      >연장하기</button>
                      <MyPageLoanModal 
                        id="dialogExtended" 
                        loanId={selectLoanId} 
                        fetchBookLoans={() => fetchBookLoans(page, size)}
                      />
                    </div>
                  </div>
                </div>
              ))}
            </div>
            {/* 페이징 */}
            <div>
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
        </>
      );
    } else {
      content = (
        <>
          <div className="my-5 w-full text-center text-gray-400">도서 대출 내역이 없습니다.</div>
        </>
      );
    }
  }

  // 확인용
  console.log(data);
  console.log("totalPages: ", totalPages);

  return (
    <>
      <div className="flex flex-col border border-gray-300 mt-2 p-8 leading-10">
        <div className="text-2xl mb-4">대출내역 정보</div>
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
        <div className="flex flex-row items-center">
          <div className="flex flex-col w-full justify-center items-center">
            { loading && <p className="text-center">불러오는 중...</p> }
            { error && <p className="text-red-700 text-center">대출 내역을 불러올 수 없습니다.</p> }
            {/* 총 개수와 페이지 */}
            <div className="self-start flex flex-row text-sm mb-1">
              <div>총 {data.length > 0 ? totalElements : 0}건</div> 
              <div className="mx-2">|</div>
              <div>{ data.length > 0 ? page + 1 : page }
                  /{ data.length > 0 ? totalPages : 0 }페이지</div>
            </div>
            { content }
          </div>
        </div>
      </div>
    </>
  );
}

export default MyPageLoan;