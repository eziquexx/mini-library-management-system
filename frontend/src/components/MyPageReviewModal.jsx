import { useEffect, useState } from "react";
import useUserStore from "../stores/useUserStore";
import { useNavigate } from "react-router-dom";
import axios from "axios";




const MyPageReviewModal = ({id, reviewId, fetchBookReview, mode, bookId}) => {
  
  const fetchUser = useUserStore((state) => state.fetchUser);

  const modalData = {
    createReview: {
      title: "리뷰 작성",
      modalId: "dialogCreateReview" + bookId,
    },
    updateReview: {
      title: "리뷰 수정",
      modalId: "dialogUpdateReview" + reviewId,
    },
    detailReview: {
      title: "리뷰 상세",
      modalId: "dialogDetailReview" + id,
    }
  }

  const { title, modalId } = modalData[mode] || {};
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [data, setData] = useState([]);
  const [originReview, setOriginReview] = useState("");
  const [updateReview, setUpdateReview] = useState("");
  const navigate = useNavigate();
  const [content, setContent] = useState("");

  // console.log("id: ", id);
  // console.log("reviewId: ", reviewId);
  // console.log("mode: ", mode);
  // console.log("bookId: ", bookId);
  // console.log("modalId: ", modalId);

  useEffect(() => {
    fetchData();
  }, [modalId]);

  // mode별로 맞는 API 호출
  const fetchData = async () => {

    setLoading(true);
    setError(null);

    try {
      if (mode === "createReview") {
        // 도서 상세 정보 불러오기
        const response = await axios.get(
          `http://localhost:8080/api/v1/books/${bookId}`,
          {
            withCredentials: true,
            headers: {
              Accept: "application/json",
            }
          }
        );

        console.log(response.data.data);
        setData(response.data.data);
      } else if (mode === "updateReview" || mode === "detailReview") {
        // 리뷰 상세 불러오기
        const response = await axios.get(
          `http://localhost:8080/api/v1/user/me/reviews/${reviewId}`,
          {
            withCredentials: true,
            headers: {
              Accept: "application/json",
            }
          }
        );

        setData(response.data.data);
        setOriginReview(response.data.data.content);
        setUpdateReview(response.data.data.content);
      }
    } catch (error) {
      console.log("Error: ", error.response);
      setError(error.response);
    } finally {
      console.log("실행 종료");
      setLoading(false);
    }
  }

  // 리뷰 수정 api
  const fetchUpdateReview = async () => {
    try {
      const response = await axios.patch(
        `http://localhost:8080/api/v1/user/me/reviews/${reviewId}`,
        { content: updateReview, },
        { withCredentials: true, }
      );

      console.log("리뷰 수정 성공");
      alert("리뷰 수정 성공했습니다.");
      return response;
    } catch (error) {
      console.log("Error: ", error.response);
      setError(error.response);
    } finally {
      console.log("리뷰 수정 완료");
    }
  }

  // 리뷰 삭제 api
  const fetchDeleteReview = async () => {
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/v1/user/me/reviews/${reviewId}`,
        { withCredentials: true, }
      );

      console.log(response.data.data);
      alert("리뷰 삭제 성공했습니다.");
      
    } catch (error) {
      console.log("Error: ", error.response);
      setError(error.response);
    } finally {
      console.log("리뷰 삭제 완료");
    }
  }

  // 리뷰 작성 api
  const fetchCreateReview = async () => {
    try {
      const response = await axios.post(
        `http://localhost:8080/api/v1/user/books/${bookId}/reviews`,
        { content: content },
        { withCredentials: true }
      );

      console.log(response.data.data);
    } catch (error) {
      console.log("Error: ", error.response);
      setError(error.response);
    } finally {
      console.log("리뷰 작성 완료");
    }
  }

  // 닫기 버튼
  const handleClose = () => {
    setUpdateReview(originReview);
  }

  // 리뷰 수정 버튼
  const handleUpdateReview = async () => {
    await fetchUpdateReview();
    await fetchBookReview();

    document.getElementById(modalId)?.close();
  }

  // 리뷰 삭제 버튼
  const handleDeleteReview = async () => {
    await fetchDeleteReview();
    await fetchBookReview();
    document.getElementById(modalId)?.close();
  }

  return (
    <>
      {/* <button command="show-modal" commandfor="dialog" className="rounded-md bg-gray-950/5 px-2.5 py-1.5 text-sm font-semibold text-gray-900 hover:bg-gray-950/10">Open dialog</button> */}
      <el-dialog>
        <dialog id={modalId} aria-labelledby="dialog-title" className="fixed inset-0 size-auto max-h-none max-w-none overflow-y-auto bg-transparent backdrop:bg-transparent">
          {/* 배경 */}
          <el-dialog-backdrop className="fixed inset-0 bg-gray-500/75 transition-opacity data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in"></el-dialog-backdrop>

          <div tabindex="0" className="flex min-h-full items-center justify-center p-4 text-center focus:outline-none sm:p-0">
            <el-dialog-panel 
              className="
                relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all 
                sm:my-8 sm:w-full sm:max-w-lg 
                w-full my-8 max-w-lg
                data-closed:translate-y-4 data-closed:opacity-0 
                data-enter:duration-300 data-enter:ease-out 
                data-leave:duration-200 data-leave:ease-in 
                data-closed:sm:translate-y-0 data-closed:sm:scale-95"
            >
              <div className="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div className="sm:flex sm:items-start">
                  {/* <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" data-slot="icon" aria-hidden="true" className="size-6 text-red-600">
                      <path d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                  </div> */}

                  
                  <div className="flex flex-col w-full sm:mt-0 sm:text-left">
                    <h3 id="dialog-title" className="text-base font-semibold text-gray-900 text-center mb-2 sm:text-left sm:mb-0">{title}</h3>
                    {/* 내용 */}
                    <div className="flex flex-col items-start mt-2">
                      {/* 도서정보 */}
                      <div className="flex flex-row w-full items-start text-sm text-gray-500">
                        {/* 썸네일 */}
                        <div className="flex w-[100px] min-w-[100px] items-start shrink-0">
                          <img src="https://placehold.co/420x600" alt="" className="w-auto block h-auto" />
                        </div>
                        <div className="flex flex-col w-full min-w-0 leading-6 text-[15px] ml-2 text-gray-700">
                          <div className="font-bold text-black">{data.bookTitle || data.title}</div>
                          <div className="flex flex-row text-black w-full">
                            <span className="text-gray-500 w-2/11 sm:w-2/12">저자</span>
                            <span className="w-8/11 sm:w-10/12">{data.author}</span>
                          </div>
                          <div className="flex flex-row text-black w-full">
                            <span className="text-gray-500 w-2/11 sm:w-2/12">출판사</span>
                            <span className="w-8/11 sm:w-10/12">{data.publisher}</span>
                          </div>
                          <div className="flex flex-row text-black w-full">
                            <span className="text-gray-500 w-2/11 sm:w-2/12">출판일</span>
                            <span className="w-8/11 sm:w-10/12">{data.publishedDate}</span>
                          </div>
                          <div className="flex flex-row text-black w-full">
                            <span className="text-gray-500 w-2/11 sm:w-2/12">ISBN</span>
                            <span className="w-8/11 sm:w-10/12">{data.isbn}</span>
                          </div>
                        </div>
                      </div>
                      {/* 리뷰내용 */}
                      <div className="flex flex-row w-full mt-2 leading-6 overflow-hidden items-center text-[15px]">
                        <textarea 
                          className="
                            w-full min-w-0 
                            min-h-[82px] max-h-[82px] h-[82px] 
                            px-2 py-1 
                            border border-gray-200 rounded line-clamp-3 
                            outline-none focus:border-teal-600
                            resize-none overflow-y-scroll"
                          value={updateReview}
                          onChange={(e) => setUpdateReview(e.target.value)}
                        >
                        </textarea>
                      </div>
                    </div>
                  </div>
                  
                </div>
              </div>
              <div 
                className="
                  px-4 pb-7 sm:pb-5 
                  flex flex-col-reverse sm:flex-row
                  sm:justify-between sm:px-6"
              >
                <div>
                  <button
                    type="button" 
                    command="close" 
                    commandfor={modalId} 
                    className="
                      order-3 sm:order-1
                      inline-flex w-full justify-center rounded-md 
                      bg-red-700 hover:bg-red-800 
                      text-sm font-semibold text-white shadow-xs 
                      px-3 py-3 sm:px-3 sm:py-2 sm:w-auto
                      disabled:bg-gray-400
                    "
                    onClick={handleDeleteReview}
                  >삭제</button>
                </div>
                <div>
                  <button 
                    type="button" 
                    command="close" 
                    commandfor={modalId} 
                    disabled={originReview === updateReview}
                    className="
                      order-1 sm:order-2
                      inline-flex w-full justify-center rounded-md 
                      bg-teal-600 hover:bg-teal-700 
                      text-sm font-semibold text-white shadow-xs 
                      px-3 py-3 mb-2 sm:mb-0 sm:px-3 sm:py-2 sm:w-auto
                      disabled:bg-gray-400
                    "
                    onClick={handleUpdateReview}
                  >변경</button>
                  <button 
                    type="button" 
                    command="close" 
                    commandfor={modalId} 
                    className="
                      order-2 sm:order-3
                      inline-flex w-full justify-center rounded-md 
                      bg-white px-3 py-3 sm:px-3 sm:py-2 mb-5 text-sm font-semibold text-gray-900 
                      shadow-xs inset-ring inset-ring-gray-300 
                      hover:bg-gray-50 sm:mt-0 sm:w-auto sm:ml-2 sm:mb-0"
                    onClick={handleClose}
                  >닫기</button>
                </div>
              </div>
            </el-dialog-panel>
          </div>
        </dialog>
      </el-dialog>
    </>
  );
}

export default MyPageReviewModal;