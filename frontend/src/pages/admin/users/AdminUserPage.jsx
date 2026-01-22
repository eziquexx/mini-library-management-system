import { useState } from "react";
import AdminHeader from "../../../components/admin/AdminHeader";
import CustomInputSearch from "../../../components/admin/CustomInputSearch";
import CustomSelect from "../../../components/admin/CustomSelect";
import CustomTable from "../../../components/admin/CustomTable";
import CustomDetailModal from "../../../components/admin/CustomDetailModal";


const AdminUserPage = () => {
  const title = ["회원관리"];
  const defaultOption = "USERNAME";
  const options = [{"USERNAME":"사용자 ID"}, {"EMAIL":"이메일"}];
  const [totalElements, setTotalElements] = useState("");
  const [open, setOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);  // 클릭한 아이템 저장
  const [isDataUpdated, setIsDataUpdated] = useState(false); // 데이터 수정 여부

  const [searchParams, setSearchParams] = useState({type: "USERNAME", keyword: ""});
  const [searchTrigger, setSearchTrigger] = useState(0);

  const apiUrl = import.meta.env.VITE_API_URL;

  const handleChildData = (data) => {
    setTotalElements(data);
  }

  // 트리거: 테이블의 tr 클릭 시
  const handleRowClick = (item) => {
    setSelectedItem(item); // 클릭한 아이템 설정
    setOpen(true);  // 모달 열기
  };

  // 데이터 수정되면 테이블 리로드
  const handleDataUpdated = () => {
    setIsDataUpdated(true); // 데이터 수정됨을 알림
  };

  // 테이블 리로드 후 상태 초기화
  const handleTableReloadComplete = () => {
    setIsDataUpdated(false); 
  };

  const handleSearchType = (value) => {
    setSearchParams(prev => ({ ...prev, type: value }));
  };

  const handleSearchKeyword = (value) => {
    setSearchParams(prev => ({ ...prev, keyword: value }));
    setSearchTrigger(prev => prev + 1);
  }
  

  return (
    <div>
      <AdminHeader title={title} />

      {/* 컨텐츠 */}
      <div className="bg-white p-5 rounded-xl shadow-[0px_0px_14px_rgba(0,0,0,0.08)]">
        <div>
          <h4 className="text-lg font-bold inline-block">회원목록</h4>
          <span className="text-sm ml-3 text-gray-400">총 {totalElements}명</span>
        </div>
        <div className="flex flex-row mt-5.5">
          <CustomSelect 
            options={options} 
            defaultOption={defaultOption} 
            handleSearchType={handleSearchType} 
          />
          <CustomInputSearch 
            handleSearchKeyword={handleSearchKeyword} 
          />
        </div>

        <div className="mt-4">
          <CustomTable 
            apiUrl={apiUrl} 
            sendData={handleChildData} 
            onRowClick={handleRowClick} 
            shouldReload={isDataUpdated} 
            onReloadComplete={handleTableReloadComplete} 
            pageType="users" 
            searchParams={searchParams}
            searchTrigger={searchTrigger}
          />
        </div>
      </div>
      
      <CustomDetailModal 
        open={open} 
        onClose={() => setOpen(false)}
        item={selectedItem} // 선택된 아이템을 Modal에 전달
        apiUrl={apiUrl}
        pageType="users"
        onDataUpdated={handleDataUpdated}
      />

      <style jsx>{`
        .custom-scrollbar {
          overflow-x: scroll; /* 수평 스크롤 활성화 */
          -ms-overflow-style: none; /* Internet Explorer에서 스크롤바 숨김 */
          scrollbar-width: none; /* Firefox에서 스크롤바 숨김 */
        }

        .custom-scrollbar::-webkit-scrollbar {
          display: none; /* Webkit 기반 브라우저에서 스크롤바 숨김 */
        }
      `}</style>
    </div>
  );
}

export default AdminUserPage;