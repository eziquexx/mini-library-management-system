import { useEffect, useState } from "react";


const CustomPagination = ({page, totalPages, handlePageClick, sendDataPage}) => {
  const [thisPage, setThisPage] = useState(0);
  const [currentGroup, setCurrentGroup] = useState(0); // 현재 페이지 그룹 (0 ~ n)
  const pagesPerGroup = 10; // 한 그룹에 표시할 페이지 수
  const totalGroups = Math.ceil(totalPages / pagesPerGroup); // 총 그룹 수

  useEffect(() => {
    setThisPage(page);
  }, [page]);


  // 페이지 그룹에서 첫 번째 페이지 번호 계산
  const getFirstPageOfGroup = (groupIndex) => {
    return groupIndex * pagesPerGroup; // 그룹의 첫 번째 페이지 번호
  };

  // 페이지 그룹에서 마지막 페이지 번호 계산
  const getLastPageOfGroup = (groupIndex) => {
    return Math.min((groupIndex + 1) * pagesPerGroup - 1, totalPages - 1); // 그룹의 마지막 페이지 번호
  };

  // 다음 버튼
  const goToNextGroup = () => {
    if (currentGroup < totalGroups - 1) {
      const nextGroupFirstPage = getFirstPageOfGroup(currentGroup + 1);
      setCurrentGroup(currentGroup + 1);
      setThisPage(nextGroupFirstPage);
      sendDataPage(nextGroupFirstPage);
    }
  };

  // 이전 버튼
  const goToPrevGroup = () => {
    if (currentGroup > 0) {
      const prevGroupLastPage = getLastPageOfGroup(currentGroup - 1);
      setCurrentGroup(currentGroup - 1);
      setThisPage(prevGroupLastPage); // 마지막 페이지로 이동
      sendDataPage(prevGroupLastPage);
    }
  };

  // 맨 앞 버튼
  const goToFirstGroup = () => {
    setCurrentGroup(0); // 첫 번째 그룹으로 이동
    setThisPage(0); // 첫 번째 페이지로 이동
    sendDataPage(0);
  };

  // 맨 뒤 버튼 
  const goToLastGroup = () => {
    const lastGroupFirstPage = getFirstPageOfGroup(totalGroups - 1); // 마지막 그룹의 첫 번째 페이지 번호
    setCurrentGroup(totalGroups - 1); // 마지막 그룹으로 이동
    setThisPage(lastGroupFirstPage); // 마지막 그룹의 첫 번째 페이지로 이동
    sendDataPage(lastGroupFirstPage);
  };

  // 표시할 페이지 번호 범위 설정
  const getPageNumbers = () => {
    const startPage = currentGroup * pagesPerGroup;
    const endPage = Math.min(startPage + pagesPerGroup, totalPages); 

    return [...Array(endPage - startPage)].map((_, i) => startPage + i);
  };

  return (
    <div className="absolute left-1/2 transform -translate-x-1/2 flex justify-center h-full">
      <div className="flex flex-row text-md h-full text-gray-500 ">
        <button 
          onClick={goToFirstGroup}
          disabled={currentGroup === 0}
          className="p-2 flex h-full items-center cursor-pointer hover:text-teal-600 disabled:hover:text-gray-600 disabled:opacity-50"
        >
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-4">
            <path strokeLinecap="round" strokeLinejoin="round" d="m18.75 4.5-7.5 7.5 7.5 7.5m-6-15L5.25 12l7.5 7.5" />
          </svg>
        </button>
        <button 
          onClick={goToPrevGroup}
          disabled={currentGroup === 0}
          className="p-2 mx-1 flex h-full items-center cursor-pointer hover:text-teal-600 disabled:hover:text-gray-600 disabled:opacity-50"
        >
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-4">
            <path strokeLinecap="round" strokeLinejoin="round" d="M15.75 19.5 8.25 12l7.5-7.5" />
          </svg>
        </button>
        <ul className="flex flex-row h-full justify-center items-center">
          {getPageNumbers().map((i) => (
            <li 
              key={i}
              onClick={() => handlePageClick(i)}
              className={`px-2.5 py-1 mx-1 cursor-pointer rounded-sm ${
                i === thisPage ? "bg-teal-600 text-white" : ""
              }`}
            >
              {i + 1}
            </li>
          ))}
        </ul>
        <button 
          onClick={goToNextGroup}
          disabled={currentGroup === totalGroups - 1}
          className="p-2 mx-1 flex justify-center items-center cursor-pointer hover:text-teal-600 disabled:hover:text-gray-600 disabled:opacity-50"
        >
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-4">
            <path strokeLinecap="round" strokeLinejoin="round" d="m8.25 4.5 7.5 7.5-7.5 7.5" />
          </svg>
        </button>
        <button 
          onClick={goToLastGroup}
          disabled={currentGroup === totalGroups - 1}
          className="p-2 flex justify-center items-center cursor-pointer hover:text-teal-600 disabled:hover:text-gray-600 disabled:opacity-50"
        >
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="size-4">
            <path strokeLinecap="round" strokeLinejoin="round" d="m5.25 4.5 7.5 7.5-7.5 7.5m6-15 7.5 7.5-7.5 7.5" />
          </svg>
        </button>
      </div>
    </div>
  );
}

export default CustomPagination;