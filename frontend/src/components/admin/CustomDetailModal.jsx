import { useEffect } from "react";
import { Dialog, DialogBackdrop, DialogPanel } from '@headlessui/react'
// import { ExclamationTriangleIcon } from '@heroicons/react/24/outline'
import AdminUserDetailModalPage from "../../pages/admin/users/AdminUserDetailModalPage";


const CustomDetailModal = ({open, onClose, item, pageType, apiUrl, onDataUpdated}) => {

  // 예시로 pageType을 외부 API로부터 받거나 사용자 입력에 따라 설정
  useEffect(() => {
    renderPage(pageType);
  }, [pageType]);

  // useEffect(() => {
  //   if (open) {
  //     console.log("모달이 열렸습니다.");
  //   }
  // }, [open]);  // open 값이 변경될 때마다 실행 

  const renderPage = () => {
    switch (pageType) {
      case "users":
        return <AdminUserDetailModalPage item={item} apiUrl={apiUrl} onClose={onClose} onDataUpdated={onDataUpdated} />;
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
              { renderPage() }
            </DialogPanel>
          </div>
        </div>
      </Dialog>
    </div>
  );
}

export default CustomDetailModal;