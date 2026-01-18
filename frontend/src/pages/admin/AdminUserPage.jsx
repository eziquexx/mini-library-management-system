import { useEffect, useState } from "react";
import AdminHeader from "../../components/admin/AdminHeader";
import CustomInputSearch from "../../components/admin/CustomInputSearch";
import CustomSelect from "../../components/admin/CustomSelect";
import CustomTable from "../../components/admin/CustomTable";
import CustomDetailModal from "../../components/admin/CustomDetailModal";


const AdminUserPage = () => {
  const title = ["회원관리"];
  const defaultOption = "사용자 ID";
  const options = ["사용자 ID", "이메일"];
  const [totalElements, setTotalElements] = useState("");
  const [open, setOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState(null);  // 클릭한 아이템 저장

  const apiUrl = `http://localhost:8080/api/v1/admin`;

  const handleChildData = (data) => {
    setTotalElements(data);
  }

  // 트리거: 테이블의 tr 클릭 시
  const handleRowClick = (item) => {
    setSelectedItem(item); // 클릭한 아이템 설정
    setOpen(true);  // 모달 열기
  };
  

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
          <CustomSelect options={options} defaultOption={defaultOption} />
          <CustomInputSearch />
        </div>

        <div className="mt-4">
          <CustomTable apiUrl={apiUrl} sendData={handleChildData} onRowClick={handleRowClick}/>
        </div>
      </div>
      
      <CustomDetailModal 
        open={open} 
        onClose={() => setOpen(false)}
        item={selectedItem} // 선택된 아이템을 Modal에 전달
        apiUrl={apiUrl}
        pageType={"userDetailPage"}
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