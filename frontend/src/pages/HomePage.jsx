import React from "react";
import { Link, useNavigate } from "react-router-dom";

const HomePage = () => {

  const navigate = useNavigate();

  const goToLoginPage = () => {
    navigate("/login");
  }

  return (
    <>
      <div className="w-full">
        <header className="w-full h-auto flex flex-col">
          <div className="w-full h-10 flex flex-row justify-between lg:px-12 px-7 leading-10 bg-teal-500 text-sm">
            <h1>로고</h1>
            <div className="flex flex-row">
              <Link to="/login">로그인</Link>
              <span className="mr-2">님</span>
              <div>마이페이지</div>
            </div>
          </div>
          <div className="w-full h-14 lg:px-12 px-7 leading-14 bg-teal-400">
            <ul className="flex flex-row">
              <li className="mr-2">홈</li>
              <li>공지사항</li>
            </ul>
          </div>
        </header>
        <div className="w-full flex flex-col px-12">
          <div className="flex flex-col mt-26 ">
            <h1 className="text-3xl font-bold text-center">행복 도서관</h1>
            <span className="text-center mt-5">행복 도서관에 오신 것을 환영합니다.</span>
          </div>
          <div className="flex justify-center mt-6">
            <form action="">
              <div className="flex flex-row">
                <input 
                  type="text" 
                  placeholder="검색어를 입력해주세요"
                  className="
                    col-span-3 
                    w-100
                    py-3 px-3 
                    border rounded-l-sm border-gray-300 
                    text-sm leading-5 
                    bg-white"
                />
                <button className="rounded-r-sm bg-teal-500 text-white px-5">검색</button>
              </div>
            </form>
          </div>
          <div className="border mt-7">
            <div class="grid grid-cols-4 gap-4">
              <div className="border p-14">01</div>
              <div className="border p-14">02</div>
              <div className="border p-14">03</div>
              <div className="border p-14">04</div>
              <div className="border p-14">05</div>
              <div className="border p-14">06</div>
              <div className="border p-14">07</div>
              <div className="border p-14">08</div>
              <div className="border p-14">09</div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default HomePage;