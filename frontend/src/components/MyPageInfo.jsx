import { useState } from "react";
import MyPageInfoModal from "./MyPageInfoModal";


const MyPageInfo = (user) => {

 const userInfo = user.user;

  if (!user) return <div>사용자 정보를 불러오는 중...</div>

  console.log(userInfo);

  return (
    <>
      <div className="flex flex-col border border-gray-300  mt-2 p-8 leading-10">
        <div className="text-2xl mb-4">기본정보</div>
        <div className="flex flex-row items-center pl-1">
          <div className="w-[90px]">이름</div>
          <div className="">{userInfo?.username ?? "데이터를 찾을 수 없습니다."}</div>
        </div>
        <div className="flex flex-row items-center pl-1">
          <div className="w-[90px]">이메일</div>
          <div>{userInfo?.email ?? "이메일을 찾을 수 없습니다."}</div>
          <button 
            command="show-modal" 
            commandfor="dialogEmail"
            className="
              ml-3 px-2 py-2 
              border border-teal-600 
              text-sm text-teal-600 leading-none 
              hover:border-teal-700
              hover:bg-teal-700
              hover:text-white
              cursor-pointer
            "
          >수정</button>
          <MyPageInfoModal id="dialogEmail"/>
        </div>
        <div className="flex flex-row items-center pl-1">
          <div className="w-[90px]">비밀번호</div>
          <div className="">****</div>
          <button 
            command="show-modal" 
            commandfor="dialogPw"
            className="
              ml-3 px-2 py-2 
              border border-teal-600 
              text-sm text-teal-600 leading-none 
              hover:border-teal-700
              hover:bg-teal-700
              hover:text-white 
              cursor-pointer
            "
          >수정</button>
          <MyPageInfoModal id="dialogPw"/>
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

export default MyPageInfo;