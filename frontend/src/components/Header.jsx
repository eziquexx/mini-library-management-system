import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import useUserStore from "../stores/useUserStore";

const Header = () => {

  const { user, fetchUser, loading, logout } = useUserStore();
  const navigate = useNavigate();

  useEffect(() => {
    fetchUser();
  }, []);

  if (loading) {
    return null
  }

  // 로그아웃
  const goToLogout = async () => {
    await logout();
    navigate('/');
  }


  // 도서검색 메뉴 클릭시 키워드 초기화
  const handleBooksMenuClick = () => {
    sessionStorage.removeItem('lastPage');
    sessionStorage.removeItem('size');
    sessionStorage.removeItem('keyword');
    sessionStorage.removeItem('type');

    setKeyword("");
    fetchBooks();
  }

  // 공지사항 메뉴 클릭시 키워드 초기화
  const handleNoticeMenuClick = () => {
    setKeyword("");
    fetchNotice();
  }

  return (
    <>
      <header className="flex flex-col">
        {/* header1 */}
        <div className="w-full flex justify-center bg-teal-700 px-7 md:px-10 lg:px-14">
          <div className="w-7xl max-w-7xl h-9 leading-9 text-sm">
            <div className="flex flex-row float-right text-white">
              {user ? (
                <>
                  <span>{user}님</span>
                  <div className="mx-3">|</div>
                  <div className="cursor-pointer">마이페이지</div>
                  <div className="mx-3">|</div>
                  <div onClick={goToLogout} className="cursor-pointer">로그아웃</div>
                </>
              ) : (
                <>
                  <Link to="/login">로그인</Link>
                  <div className="mx-3">|</div>
                  <Link to="/signup">회원가입</Link>
                </>
              )}
            </div>
          </div>
        </div>
        {/* header2 */}
        <div className="w-full flex justify-center bg-white border-b border-teal-600 px-7 md:px-10 lg:px-14">
          <div className="w-7xl max-w-7xl flex justify-between h-18 leading-18 text-lg">
            <h1><Link to="/" className="block font-bold text-white bg-teal-600 px-7">행복 도서관</Link></h1>
            <ul className="flex flex-row space-x-8">
              <li><Link to="/">홈</Link></li>
              <li><Link to="/books" onClick={ handleBooksMenuClick }>도서검색</Link></li>
              <li><Link to="/notice" onClick={ handleNoticeMenuClick }>공지사항</Link></li>
            </ul>
          </div>
        </div>
      </header>
    </>
  );
}

export default Header;