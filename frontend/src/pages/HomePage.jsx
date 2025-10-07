import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

const HomePage = () => {

  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await axios.get("http://localhost:8080/api/v1/user/me", 
          { withCredentials: true,
            headers: {
              Accept: "application/json",
            },
          });
          setUser(response.data.data.username);
          console.log(response.data.data);
      } catch(error) {
        // 로그인 안된 상태 체크하여 user에 null값 유지
        if (error.response && error.response.status === 401) {
          setUser(null);
        }
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  if (loading) {
    return <div>로딩 중...</div>
  }

  const goToLoginPage = () => {
    navigate("/login");
  }

  // 로그아웃
  const goToLogout = async () => {
    await axios.post(
      "http://localhost:8080/api/v1/auth/logout",
      null,
      { withCredentials: true }
    );
    setUser(null);
  }

  return (
    <>
      <div className="w-full">
        <header className="flex flex-col">
          {/* header1 */}
          <div className="w-full flex justify-center bg-teal-800 px-7 md:px-10 lg:px-14">
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
                    <Link to="/login">회원가입</Link>
                  </>
                )}
              </div>
            </div>
          </div>
          {/* header2 */}
          <div className="w-full flex justify-center bg-white border-b border-teal-700 px-7 md:px-10 lg:px-14">
            <div className="w-7xl max-w-7xl flex justify-between h-18 leading-18 text-lg">
              <h1><Link to="/" className="block font-bold text-white bg-teal-700 px-7">행복 도서관</Link></h1>
              <ul className="flex flex-row space-x-8">
                <li><Link to="/">홈</Link></li>
                <li><Link to="/notice">공지사항</Link></li>
              </ul>
            </div>
          </div>
        </header>
        <div className="w-full flex justify-center px-7 md:px-10 lg:px-14">
          <div className="w-7xl max-w-7xl flex flex-col overflow-hidden">
            <div className="w-full flex flex-col mt-26 ">
              <h1 className="text-3xl font-bold text-center">행복 도서관</h1>
              <span className="text-center mt-5">행복 도서관에 오신 것을 환영합니다.</span>
            </div>
            <div className="w-full flex justify-center mt-6">
              <form action="">
                <div className="flex flex-row">
                  <input 
                    type="text" 
                    placeholder="검색어를 입력해주세요"
                    className="
                      col-span-3 
                      w-100
                      grow
                      py-3 px-3 
                      border rounded-l-sm border-gray-300 
                      text-sm leading-5 
                      bg-white"
                  />
                  <button className="rounded-r-sm bg-teal-700 text-white px-5 flex-none">검색</button>
                </div>
              </form>
            </div>
            <div className="border mt-7 overflow-hidden">
              <div className="grid grid-cols-4 gap-4">
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