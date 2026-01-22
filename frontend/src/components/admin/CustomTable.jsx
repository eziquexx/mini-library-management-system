import axios from "axios";
import { useEffect, useState } from "react";
import CustomPagination from "./CustomPagination";
import AdminUserTable from "../../pages/admin/users/AdminUserTable";

const CustomTable = ({
  apiUrl, 
  pageType, 
  searchParams, 
  searchTrigger,
  sendData, 
  onRowClick, 
  shouldReload, 
  onReloadComplete
}) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const size = 15;
  const [posts, setPosts] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  

  useEffect(() => {
    tableTypeRenderPage(pageType);
  }, [pageType]);

  const tableTypeRenderPage = () => {
    switch (pageType) {
      case "users":
        return <AdminUserTable onRowClick={onRowClick} postList={posts} />;
      default:
        return <div>Loading...</div>;
    }
  };

  // 기본 전체 조회
  const fetchData = async () => {
    setLoading(true);
    setError(null);

    console.log(loading, error);

    try {
      // 키워드가 있으면 '/search', 없으면 기본 경로 사용
      const isSearch = searchParams?.keyword && searchParams.keyword.trim() !== "";
      const url = isSearch 
        ? `${apiUrl}/${pageType}/search` 
        : `${apiUrl}/${pageType}`;

      console.log(`🚀 요청 URL: ${url}`);

      const response = await axios.get(
        url,
        {
          params: {
            page: page,
            size: size,
            type: searchParams?.type,
            keyword: searchParams?.keyword,
          },
          withCredentials: true,
          headers: {
            Accept: "application/json",
          }
        }
      );

      console.log(response);

      setPosts(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
      sendTotalElements(totalElements);

      sendData(response.data.data.totalElements || 0);

      onReloadComplete();
    } catch (error) {
      setError(error);
      console.log("Error: ", error);
    } finally {
      setLoading(false);
    }
  }

  const sendTotalElements = (totalElements) => {
    sendData(totalElements);
  }

  const handleDataPage = (thisPage) => {
    setPage(thisPage);
  }

  const handlePageClick = (pageNumber) => {
    setPage(pageNumber);
  }

  useEffect(() => {
    console.log("searchTrigger 변경 감지됨!");
    fetchData();
  }, [page, size, searchTrigger, shouldReload]);

  
  return (
    <div className="flex flex-col justify-center">
      <table className="
          table-fixed border-collapse
          text-xs w-full h-full min-w-[1400px] "
        >
        {/* thead, tbody 부분 */}
        { tableTypeRenderPage() }
      </table>

      <div className="flex relative mt-4 h-10">
        <div className="flex items-center text-sm pl-3 text-gray-600">
          <span className="mr-1">{page+1}</span>
          <span className="mr-1">/</span>
          <span className="mr-1">{totalPages}</span>
          <span>페이지</span>
        </div>
        {/* 페이징 */}
        <CustomPagination page={page} totalPages={totalPages} handlePageClick={handlePageClick} sendDataPage={handleDataPage}/>
      </div>
    </div>
  );
}

export default CustomTable;