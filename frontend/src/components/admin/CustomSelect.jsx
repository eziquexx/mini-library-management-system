import { useEffect, useRef, useState } from "react";


const CustomSelect = ({ options, defaultOption }) => {
  const [selectedValue, setSelectedValue] = useState(defaultOption);
  const [isOpen, setIsOpen] = useState(false); // 드롭다운
  const dropdownRef = useRef(null); // 드롭다운 외부 클릭 감지

  // 옵션 클릭 시 선택된 값 설정
  const handleOptionClick = (value) => {
    setSelectedValue(value);
    setIsOpen(false);
  }

  // 드롭다운 열기/닫기
  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  }

  // 바깥 클릭 감지
  useEffect(() => {
    const handleClickOutside = (event) => {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    }

    // 마운트 될 때 이벤트 리스너 추가
    document.addEventListener('click', handleClickOutside);

    // 언마운트 될 때 이벤트 리스너 제거
    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  }, []);

  return (
    <>
      {/* 커스텀 select */}
      <div 
        className="
          custom-select
          inline-block w-[160px] h-[42px]
          border border-gray-300 
          rounded-md 
          text-gray-600 text-sm 
          bg-white
          cursor-pointer z-10"
        ref={dropdownRef}
        >
        <div 
          className="selected-option flex flex-row w-full h-full pl-3 bg-white rounded-md "
          onClick={toggleDropdown}
        >
          <span className="justify-self-center self-center">{selectedValue}</span>
          <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="ml-auto mr-3 h-full size-3">
            <path strokeLinecap="round" strokeLinejoin="round" d="m19.5 8.25-7.5 7.5-7.5-7.5" />
          </svg>
        </div>

        {/* 드롭다운 옵션들 */}
        {isOpen && (
          <ul className="options border border-gray-300 rounded-md bg-white overflow-hidden">
            {options.map((option, index) => (
              <li 
                key={index}
                className="option p-2 py-2.5 hover:bg-teal-600 hover:text-white"
                onClick={() => handleOptionClick(option)}
              >{option}</li>
            ))}
          </ul>
        )}
      </div>
    </>
  );
}

export default CustomSelect;