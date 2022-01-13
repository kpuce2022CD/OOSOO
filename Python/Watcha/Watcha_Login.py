# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By

# chrome창(웹드라이버) 열기
driver = webdriver.Chrome()

# 실행할 웹페이지 불러오기(왓챠 로그인 화면)
driver.get("https://watcha.com/sign_in")
#driver.set_window_position(0, 0)
#driver.set_window_size(1900, 1000)

# 로그인
driver.find_element_by_name('email').send_keys('아이디 입력')  # 좌측 괄호 안에 회원 아이디(이메일) 입력
driver.find_element_by_name('password').send_keys('비밀번호 입력')  # 좌측 괄호 안에 회원 비밀번호 입력
driver.find_element_by_class_name('css-11a3zmg').click()

# 프로필 선택 화면 로딩이 완료될때까지 대기
tag = WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1blyq76")))

# 프로필 선택
profiles = driver.find_elements_by_class_name('css-1blyq76')

for profile in profiles:
    print(profile.text)
    if profile.text == "프로필명 입력":  # 죄측 큰따옴표 안에 회원 프로필명 입력
        profile.click()
