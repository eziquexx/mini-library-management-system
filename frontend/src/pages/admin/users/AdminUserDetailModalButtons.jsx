

const AdminUserDetailModalButtons = ({onClose}) => {

  const childValChange = true;
  return (

    <div className="bg-gray-50 px-7 py-4 flex flex-row justify-end">
      <button
        type="button"
        // onClick={onUpdateValue}
        disabled={!childValChange}
        className={`inline-flex justify-center rounded-md ${childValChange ? "bg-red-600 hover:bg-red-500" : ""} bg-gray-400 px-3 py-2 text-sm font-semibold text-white shadow-xs sm:w-auto mr-3`}
      >
        수정
      </button>
      <button
        type="button"
        data-autofocus
        onClick={onClose}
        className="inline-flex justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs inset-ring inset-ring-gray-300 hover:bg-gray-50 sm:w-auto"
      >
        닫기
      </button>
    </div>
  );
}

export default AdminUserDetailModalButtons;