# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time

# chrome창(웹드라이버) 열기
driver = webdriver.Chrome()

# 실행할 웹페이지 불러오기(왓챠 로그인 화면)
driver.get("https://watcha.com/sign_in")
# driver.set_window_position(0, 0)
# driver.set_window_size(1900, 1000)


# 로그인
driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[1]/input').send_keys('아이디')  # 좌측 괄호 안에 회원 아이디(이메일) 입력
driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[2]/input').send_keys('비밀번호')  # 좌측 괄호 안에 회원 비밀번호 입력
driver.find_element_by_xpath('//*[@id="root"]/div[1]/main/div[1]/main/div/form/div[3]/button').click()

# 프로필 선택 화면 로딩이 완료될때까지 대기
WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1blyq76")))
time.sleep(2)

# 프로필 선택
profiles = driver.find_elements_by_class_name('css-1blyq76')

for profile in profiles:
    print(profile.text)
    if profile.text == "프로필명":  # 좌측 큰따옴표 안에 회원 프로필명 입력
        profile.click()

WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.XPATH, '//*[@id="top_navigation"]/nav/div[1]/div/div/form/label/input')))
time.sleep(2)

search_title = '이터널 선샤인'  # 좌측 작은따옴표 안에 회원 영화 타이틀 입력

search = driver.find_element_by_xpath('//*[@id="top_navigation"]/nav/div[1]/div/div/form/label/input')
search.send_keys(search_title)
search.send_keys(Keys.RETURN)

time.sleep(3)
# WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1hqk0rn")))

results = driver.find_elements_by_class_name('css-1hqk0rn')

for result in results:
    print(result.text)
    if result.text == search_title:
        webdriver.ActionChains(driver).move_to_element(result).perform()
        time.sleep(1)
        driver.find_element_by_class_name('css-16g6nz2').click()




