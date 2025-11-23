import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useUserStore from "../stores/useUserStore";



const BookDetailPage = () => {

  const { user, logout } = useUserStore();
  const { bookId } = useParams();
  const [post, setPost] = useState('');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();
  const page = sessionStorage.getItem('lastPage');
  const size = sessionStorage.getItem('size');
  const keyword = sessionStorage.getItem('keyword');
  const type = sessionStorage.getItem('type');
  const [bookLike, setBookLike] = useState('');
  const [reviews, setReviews] = useState([]);
  const [reviewPage, setReviewPage] = useState("");
  const reviewSize = 5;

  const apiUrl = import.meta.env.VITE_API_URL;

  // 도서 상세 api
  const fetchPost = async (bookId) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        `${apiUrl}/api/v1/books/${bookId}`,
        {
          withCredentials: true,
          headers: {
            Accept: "application/json",
          },
        }
      );

      console.log(response.data);
      setPost(response.data.data);
    } catch(error) {
      console.log("Error: ", error.response.data);
      setError(error.response.data);
    } finally {
      setLoading(false);
    }
  }

  // 해당 도서 리뷰 api
  const fetchBookReview = async (bookId) => {
    setLoading(true);
    setError(null);

    try {
      const response = await axios.get(
        `${apiUrl}/api/v1/books/${bookId}/reviews`,
        { 
          withCredentials: true, 
          headers: "application/json"
        }
      );

      console.log(response.data.data.content);
      setReviews(response.data.data.content);
      setReviewPage(response.data.data.page);
    } catch (error) {
      console.log("Error: ", error.response.data);
      setError(error.response.data);
    }
  }

  useEffect(() => {
    if (bookId) {
      fetchPost(bookId);
      fetchBookReview(bookId);
    }
  }, [bookId])

  // 목록페이지로 이동
  const handleGoBack = () => {

    if (keyword != null) {
      navigate(`/books?type=${type}&keyword=${keyword}&page=${page}&size=${size}`);
    } else {
      navigate(`/books?page=${page}&size=${size}`);
    }
  }

  // 대출 가능
  let bookStatus;
  switch (post.status) {
    case 'AVAILABLE':
      bookStatus = '대출 가능';
      break;
    case 'BORROWED': // 대출 중
      bookStatus = '대출 불가';
      break;
    case 'RESERVED': // 예약 중
      bookStatus = '대출 불가';
      break;
    case 'LOST': // 분실됨
      bookStatus = '대출 불가';
      break;
    default:
      bookStatus = '';
      console.log("올바르지 않은 도서 상태입니다.");
      break;
  }

  // 책 소개 줄바꿈
  // data 있으면 .replace 실행. .replace가 안된다면 '' 빈값을 출력
  const postDescription = post.description?.replace(/\\n/g, '\n') ?? '';

  // 리뷰 출력 조건
  let reviewContent;
  if (reviews && reviews.length > 0) {
    reviewContent = (
      <>
        {reviews.map((review) => (
          <div 
            key={review.id}
            className="mb-2 pb-3 border-b-1 border-gray-200"
          >
            <div className="flex flex-row mb-1 px-2">
              <div className="text-teal-700 text-lg leading-7 mr-2 ">{review.username}</div>
              <div className="text-sm leading-7">- 2025/10/10</div>
            </div>
            <div className="px-2 text-md wrap-break-word">{review.content}</div>
          </div>
        ))}
      </>
    );
  } else {
    reviewContent = (
      <div className="text-md text-gray-500 text-center py-3">
        작성된 리뷰가 없습니다.
      </div>
    );
  }

  // 확인용
  // console.log(...[post]);
  // console.log("page: ", page);
  // console.log("size: ", size);
  // console.log("type: ", type);
  // console.log("keyword: ", keyword);
  // console.log("user: ", user);

  return (
    <>
      <div className="w-full">
        <div className="w-full flex justify-center px-7 md:px-10 lg:px-14 py-30">
          <div className="w-7xl max-w-7xl flex flex-col items-start overflow-hidden">

            {/* 타이틀 */}
            <div className="w-full flex flex-col">
              <h1 className="text-3xl font-bold text-center">도서 상세페이지</h1>
              <span className="text-center mt-5">도서 상세페이지입니다.</span>
            </div>

            {/* 도서 정보 */}
            { loading && <p>불러오는 중...</p> }
            { error && <p style={{ color: "red"}}>{error}</p> }

            { !loading && !error && (
              <>
                <div className="w-full flex flex-col justify-center items-center mt-6 ">
                  <div className="w-full lg:w-[900px]">
                    <div className="flex sm:flex-row flex-col border border-zinc-200">
                      <div className="sm:flex-2 flex-2 max-w-[260px] min-w-[200px] p-4 sm:self-start self-center">
                        <img src="https://placehold.co/900x1200" alt="" className="w-full h-auto" />
                      </div>
                      <div className="flex flex-col justify-between items-center sm:items-start sm:flex-4 flex-4 px-1 py-5 leading-7  bg-zinc-100 sm:bg-white">
                        <div className="sm:w-full w-5/6">
                          <div className="sm:text-2xl text-xl font-bold">{post.title}</div>
                          <div className="text-gray-800 sm:text-[16px] text-[14px] mt-2">{post.author} | {post.publisher} | {post.publishedDate}</div>
                          <div className="text-gray-800 sm:text-[16px] text-[14px]">ISBN: {post.isbn}</div>
                          <div className="text-gray-800 sm:text-[16px] text-[14px]">위치: {post.location}</div>
                          <div className="text-gray-800 sm:text-[16px] text-[14px]">
                            대출 상태:
                            { post.status == "AVAILABLE" ? 
                            <span className="text-emerald-600"> {bookStatus}</span> :
                            <span className="text-red-600"> {bookStatus}</span> 
                          }
                          </div>
                        </div>
                        <button 
                          className="
                            border 
                            px-4 py-2 mt-8 
                            sm:text-[16px] text-[14px] 
                            sm:self-start self-center 
                            hover:border-teal-600 hover:text-teal-600 
                            disabled:border-gray-400 
                            disabled:text-gray-400
                            disabled:hover:border-gray-400 disabled:hover:text-gray-400"
                        >
                          {bookLike != null ? "🤍" : "❤️"} 찜하기</button>
                      </div>
                    </div>
                    <div className="mt-5 border border-zinc-200">
                      <h3 className="sm:text-2xl text-xl font-bold bg-zinc-200 px-4 py-2">책 소개</h3>
                      <p className="sm:text-[16px] text-[14px] bg-white p-4 whitespace-pre-line">{postDescription}</p>
                    </div>

                    {/* 리뷰 */}
                    <div className="mt-5 border border-zinc-200">
                      <h3 className="sm:text-2xl text-xl font-bold bg-zinc-200 px-4 py-2">리뷰</h3>
                      <div className="sm:text-[16px] text-[14px] bg-white p-4 whitespace-pre-line">
                        { reviewContent }
                      </div>
                    </div>
                  </div>
                </div>
              </>
            )}

            <button 
              onClick={ handleGoBack }
              className="py-3 px-6 mt-6 border bg-teal-600 text-white rounded-md hover:bg-teal-700 cursor-pointer self-center"
            >목록</button>
          </div>
        </div>
      </div>
    </>
  );
}

export default BookDetailPage;