import axios from "axios";
import { useEffect, useState } from "react";
import CustomPagination from "./CustomPagination";
import AdminUserTable from "../../pages/admin/users/AdminUserTable";

const CustomTable = ({apiUrl, type, sendData, onRowClick, shouldReload, onReloadComplete}) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [page, setPage] = useState(0);
  const size = 15;
  const [posts, setPosts] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);

  useEffect(() => {
    tableTypeRenderPage(type);
  }, [type]);

  const tableTypeRenderPage = () => {
    switch (type) {
      case "users":
        return <AdminUserTable onRowClick={onRowClick} postList={posts} />;
      default:
        return <div>Loading...</div>;
    }
  };

  const fetchPost = async (page, size) => {
    setLoading(true);
    setError(null);
    console.log("loading: ", loading);
    console.log("error: ", error);

    try {
      let response;

      response = await axios.get(
        `${apiUrl}/${type}`,
        {
          params: {
            page: page,
            size: size,
          },
          withCredentials: true,
          headers: {
            Aceept: "application/json",
          }
        }
      );

      setPosts(response.data.data.content);
      setTotalPages(response.data.data.totalPages || 1);
      setTotalElements(response.data.data.totalElements || 1);
      sendTotalElements(totalElements);

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
    fetchPost(page, size).then(() => { 
      sendTotalElements(totalElements); // 비동기 요청 후 부모에게 데이터 전달
    });
  }, [page, size, totalElements, shouldReload]);

  

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