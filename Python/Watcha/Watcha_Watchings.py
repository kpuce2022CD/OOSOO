# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import time

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


# ----------------------------------------------------------------------------------------------------------------------#

# '이어보기' 에 있는 항목들 불러오기
driver.get("https://watcha.com/watchings")

# 페이지 스크롤
last_height = driver.execute_script("return document.body.scrollHeight")

while True:
    scroll_down = 0
    while scroll_down < 10:
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(0.2)
        scroll_down += 1

    # 스크롤 내린 후 스크롤 높이 다시 가져옴
    new_height = driver.execute_script("return document.body.scrollHeight")
    if new_height == last_height:
        break
    last_height = new_height

watchings = driver.find_elements_by_class_name('css-1g3awd')
print("< Watching List >")
for watch in watchings:
    print(watch.text)
