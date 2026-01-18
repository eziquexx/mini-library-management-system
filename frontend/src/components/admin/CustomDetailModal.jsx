import { useEffect, useState } from "react";
import { Dialog, DialogBackdrop, DialogPanel, DialogTitle } from '@headlessui/react'
import { ExclamationTriangleIcon } from '@heroicons/react/24/outline'
import AdminUserDetailPage from "../../pages/admin/AdminUserDetailPage";


const CustomDetailModal = ({open, onClose, item, pageType, apiUrl}) => {

  let modalContent = {
    userDetailPage: {
      title: "사용자 상세페이지",
      button1: "수정",
      button2: "닫기",
    },
    bookDetailPage: {
      title: "도서 상세페이지",
      button1: "수정",
      button2: "닫기",
    }
  };

  console.log("item: ", item);

  const content = modalContent[pageType] || { title: "Unknown Page", button1: "", button2: "" };
  // 예시로 pageType을 외부 API로부터 받거나 사용자 입력에 따라 설정
  useEffect(() => {
    renderPage(pageType);
  }, [pageType]);

  useEffect(() => {
    if (open) {
      // 모달이 열렸을 때 추가 작업이 필요하다면 여기에 추가
      console.log("모달이 열렸습니다.");
    }
  }, [open]);  // open 값이 변경될 때마다 실행


  const renderPage = () => {
    switch (pageType) {
      case "userDetailPage":
        return <AdminUserDetailPage item={item} apiUrl={apiUrl} />;
      default:
        return <div>Loading...</div>;
    }
  };

  return (
    <div>
      <Dialog open={open} onClose={onClose} className="relative z-10">
        <DialogBackdrop
          transition
          className="fixed inset-0 bg-gray-500/75 transition-opacity data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in"
        />

        <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
          <div className="flex min-h-full justify-center p-4 text-center items-center ">
            <DialogPanel
              transition
              className="relative transform overflow-hidden rounded-lg bg-white text-left shadow-xl transition-all data-closed:translate-y-4 data-closed:opacity-0 data-enter:duration-300 data-enter:ease-out data-leave:duration-200 data-leave:ease-in sm:my-8 sm:w-full sm:max-w-lg data-closed:sm:translate-y-0 data-closed:sm:scale-95"
            >
              <div className="bg-white px-3 pt-5 pb-4 ">
                <div className="sm:flex sm:items-start">
                  {/* <div className="mx-auto flex size-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:size-10">
                    <ExclamationTriangleIcon aria-hidden="true" className="size-6 text-red-600" />
                  </div> */}
                  <div className="mx-3 text-left">
                    <div className="flex flex-row justify-end text-gray-700">
                      <button onClick={onClose} className="cursor-pointer">
                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" class="size-6">
                          <path strokeLinecap="round" strokeLinejoin="round" d="M6 18 18 6M6 6l12 12" />
                        </svg>
                      </button>
                    </div>
                    <DialogTitle as="h3" className="text-base text-center font-semibold text-gray-900 sm:text-left mt-1">
                      {content.title}
                    </DialogTitle>
                    <div className="mt-2">
                      <p className="text-sm text-gray-500">
                        {renderPage()}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
              <div className="bg-gray-50 px-7 py-4 flex flex-row justify-end">
                <button
                  type="button"
                  onClick={onClose}
                  className="inline-flex justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-red-500 sm:w-auto mr-3"
                >
                  {content.button1}
                </button>
                <button
                  type="button"
                  data-autofocus
                  onClick={onClose}
                  className="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs inset-ring inset-ring-gray-300 hover:bg-gray-50 sm:w-auto"
                >
                  {content.button2}
                </button>
              </div>
            </DialogPanel>
          </div>
        </div>
      </Dialog>
    </div>
  );
}

export default CustomDetailModal;