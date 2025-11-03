


const MyPageReviewModal = ({id}) => {
  console.log("id: ", id);
  return (
    <>
      {/* <button command="show-modal" commandfor="dialog" class="rounded-md bg-gray-950/5 px-2.5 py-1.5 text-sm font-semibold text-gray-900 hover:bg-gray-950/10">Open dialog</button> */}
      <el-dialog>
        <dialog id={id} aria-labelledby="dialog-title" class="fixed inset-0 size-auto max-h-none max-w-none overflow-y-auto bg-transparent backdrop:bg-transparent">
          {/* 배경 */}
          <el-dialog-backdrop class="fixed inset-0 bg-gray-500/75 transition-opacity data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in"></el-dialog-backdrop>

          <div tabindex="0" class="flex min-h-full items-end justify-center p-4 text-center focus:outline-none sm:items-center sm:p-0">
            <el-dialog-panel class="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all data-closed:translate-y-4 data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in sm:my-8 sm:w-full sm:max-w-lg data-closed:sm:translate-y-0 data-closed:sm:scale-95">
              <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                <div class="sm:flex sm:items-start">
                  {/* <div class="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                    <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" data-slot="icon" aria-hidden="true" class="size-6 text-red-600">
                      <path d="M12 9v3.75m-9.303 3.376c-.866 1.5.217 3.374 1.948 3.374h14.71c1.73 0 2.813-1.874 1.948-3.374L13.949 3.378c-.866-1.5-3.032-1.5-3.898 0L2.697 16.126ZM12 15.75h.007v.008H12v-.008Z" stroke-linecap="round" stroke-linejoin="round" />
                    </svg>
                  </div> */}

                  { id === "dialogtest" && (
                    <>
                      <div class="flex flex-col w-full mt-3 text-center sm:mt-0 sm:text-left">
                        <h3 id="dialog-title" class="text-base font-semibold text-gray-900">리뷰 상세</h3>
                        {/* 내용 */}
                        <div class="flex flex-col items-start mt-2">
                          {/* 도서정보 */}
                          <div class="flex flex-row items-start text-sm text-gray-500">
                            {/* 썸네일 */}
                            <div className="flex w-[100px] min-w-[100px] items-start shrink-0">
                              <img src="https://placehold.co/420x600" alt="" className="w-auto block h-auto" />
                            </div>
                            <div className="flex flex-col justify-start self-start w-full min-w-0 leading-6 text-[15px] ml-2 text-gray-700">
                              <div className="font-bold text-black">책제목</div>
                              <div className="text-black">저자</div>
                              <div className="text-black">출판사</div>
                              <div className="text-black">출판일</div>
                              <div className="text-black">ISBN</div>
                            </div>
                          </div>
                          {/* 리뷰내용 */}
                          <div className="flex flex-row w-full mt-2 leading-6 overflow-hidden items-center">
                            <div className="w-full min-w-0 px-2 py-1 h-[82px] border border-gray-200 rounded line-clamp-3">
                              리뷰내용
                            </div>
                          </div>
                        </div>
                      </div>
                    </>
                  )}

                </div>
              </div>
              <div class="px-4 pb-7 sm:pb-5 sm:flex sm:flex-row-reverse sm:px-6">
                <button 
                  type="button" command="close" commandfor={id} 
                  class="
                    inline-flex w-full justify-center rounded-md 
                    bg-teal-600 hover:bg-teal-700 
                    text-sm font-semibold text-white shadow-xs 
                    px-3 py-2 sm:ml-3 sm:w-auto
                  "
                >변경</button>
                <button type="button" command="close" commandfor={id} class="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs inset-ring inset-ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto">취소</button>
              </div>
            </el-dialog-panel>
          </div>
        </dialog>
      </el-dialog>
    </>
  );
}

export default MyPageReviewModal;