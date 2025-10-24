

const MyPageReview = () => {
  return (
    <>
      <div className="flex flex-col border border-gray-300  mt-2 p-8 leading-10">
        <div className="text-2xl mb-4">리뷰내역 정보</div>
        <div className="flex flex-row items-center pl-1">
          <div className="w-[90px]">이름</div>
          <div className="">테스트</div>
        </div>
        <div className="flex flex-row items-center pl-1">
          <div className="w-[90px]">이메일</div>
          <div>테스트</div>
          <div className="
            ml-3 px-2 py-2 
            border border-teal-600 
            text-sm text-teal-600 leading-none 
            hover:border-teal-700
            hover:bg-teal-700
            hover:text-white
            cursor-pointer
          ">수정</div>
        </div>
        <div className="flex flex-row items-center pl-1">
          <div className="w-[90px]">비밀번호</div>
          <div className="">****</div>
          <div className="
            ml-3 px-2 py-2 
            border border-teal-600 
            text-sm text-teal-600 leading-none 
            hover:border-teal-700
            hover:bg-teal-700
            hover:text-white 
            cursor-pointer
          ">수정</div>
        </div>
        <div className="mt-5 mb-7 border-b-1 border-gray-300"></div>
        <div 
          className="px-4 py-3 
            border border-gray-500 
            self-start leading-none
            text-gray-500 
            hover:border-red-700
            hover:text-red-700
            cursor-pointer
          ">회원탈퇴</div>
      </div>
    </>
  );
}

export default MyPageReview;