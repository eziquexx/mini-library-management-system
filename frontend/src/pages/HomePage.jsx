import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const HomePage = () => {

  return (
    <>
      <div className="w-full border">
        <div className="w-full flex justify-center px-7 md:px-10 lg:px-14 py-30">
          <div className="w-7xl max-w-7xl flex flex-col overflow-hidden">
            <div className="w-full flex flex-col">
              <h1 className="text-3xl font-bold text-center">행복 도서관</h1>
              <span className="text-center mt-5">행복 도서관에 오신 것을 환영합니다.</span>
            </div>
            <div className="w-full flex justify-center mt-6">
              <form className="w-full md:w-120">
                <div className="flex">
                  <input 
                    type="text" 
                    placeholder="검색어를 입력해주세요"
                    className="
                      w-5/6
                      py-4 px-5
                      border rounded-l-sm border-gray-300 
                      text-sm leading-5 
                      bg-white"
                  />
                  <button className="rounded-r-sm bg-teal-600 hover:bg-teal-700 text-white px-4 w-1/6 cursor-pointer">검색</button>
                </div>
              </form>
            </div>
            <div className="border mt-7 overflow-hidden">
              <div className="grid lg:grid-cols-4 lg:gap-4 md:grid-cols-3 md:gap-3 sm:grid-cols-2 sm:gap-2 grid-cols-1 gap-3">
                <div className="border p-14">01</div>
                <div className="border p-14">02</div>
                <div className="border p-14">03</div>
                <div className="border p-14">04</div>
                <div className="border p-14">05</div>
                <div className="border p-14">06</div>
                <div className="border p-14">07</div>
                <div className="border p-14">08</div>
                <div className="border p-14">09</div>
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
      </div>
    </>
  );
}

export default HomePage;