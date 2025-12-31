import { useEffect, useState } from "react";
import useUserStore from "../../stores/useUserStore";
import MyPageInfo from "../../components/users/MyPageInfo";
import MyPageLoan from "../../components/users/MyPageLoan";
import MyPageReview from "../../components/users/MyPageReview";


const MyPage = () => {

  const { user, fetchUser, logout } = useUserStore();
  // const username = user?.username;
  const [activeTab, setActiveTab] = useState("info");

  // useEffect(() => {
  //   if (!user) fetchUser();
  // }, []);
  
  // console.log("user: ", user);
  // console.log("username: ", username);

  return (
    <>
      <div>
        <div className="w-full flex justify-start flex-col px-7 md:px-10 lg:px-14 py-30">
          {/* 타이틀 */}
          <div className="w-full">
            <h1 className="text-3xl font-bold text-center">마이페이지</h1>
            <span className="block text-center mt-5">기본정보 및 대출/리뷰 내역으로 이동할 수 있는 페이지입니다.</span>
          </div>

          {/* 기본정보 및 대출/리뷰내역 */}
          <div className="w-full flex flex-col justify-center items-center mt-6 ">
            <div className="w-full lg:w-[900px]">
              <div className="mt-5">
                {/* tab 메뉴 */}
                <div className="flex flex-row text-center">
                  <div 
                    className={`flex-1 py-3 border-b-1 ${
                      activeTab === "info" 
                        ? "border-1 border-teal-600 text-teal-600" 
                        : "border-gray-400 text-gray-500 text-gray-500"
                    }`}
                    onClick={() => setActiveTab("info")}
                  >개인정보</div>
                  <div 
                    className={`flex-1 py-3 border-b-1 ${
                      activeTab === "loan" 
                        ? "border-1 border-teal-600 text-teal-600" 
                        : "border-gray-400 text-gray-500"
                    }`}
                    onClick={() => setActiveTab("loan")}
                  >대출내역</div>
                  <div className={`flex-1 py-3 border-b-1 ${
                      activeTab === "review" 
                        ? "border-1 border-teal-600 text-teal-600" 
                        : "border-gray-400 text-gray-500 text-gray-500"
                    }`}
                    onClick={() => setActiveTab("review")}
                  >리뷰내역</div>
                </div>
                {/* 컨텐츠영역 */}
                <div>
                  {/* 개인정보 */}
                  {activeTab === "info" && user && <MyPageInfo user={user}/>}
                  {activeTab === "loan" && <MyPageLoan />}
                  {activeTab === "review" && <MyPageReview />}
                  <div></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}

export default MyPage;