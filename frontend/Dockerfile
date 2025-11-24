# 1단계: 빌드
# Build stage
FROM node:20-alpine as build

# 작업 디렉토리 설정
WORKDIR /app

# package.json과 package-lock.json 먼저 복사 (캐시 무효화)
COPY package*.json ./

# 의존성 설치
RUN npm install

# 소스 코드 전체 복사
COPY . .

# 빌드 시 env 전달
ARG VITE_API_URL
ENV VITE_API_URL=$VITE_API_URL

# 여기서 React/Vite 앱 빌드
RUN npm run build

# 2단계: Nginx 서빙
# Production stage
FROM nginx:stable-alpine

# 빌드된 React 앱을 Nginx의 기본 HTML 디렉토리로 복사
COPY --from=build /app/dist /usr/share/nginx/html

# Nginx 설정 파일을 덮어쓰기. frontend/nginx.conf 파일을 컨테이너 내부 Nginx 설정으로 덮어씀
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 80 포트 노출
EXPOSE 80

# Nginx 서버 실행..
CMD ["nginx", "-g", "daemon off;"]
