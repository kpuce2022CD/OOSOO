# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from pyvirtualdisplay import Display
import time


def w_wishes():
    result = list()

    display = Display(visible=0, size=(1920, 1080))
    display.start()

    # chrome창(웹드라이버) 열기
    driver = webdriver.Chrome("/home/ubuntu/django_server/chromedriver")

    # 실행할 웹페이지 불러오기(왓챠 로그인 화면)
    driver.get("https://watcha.com/sign_in")
    # driver.set_window_position(0, 0)
    # driver.set_window_size(1900, 1000)

    # 로그인
    driver.find_element_by_name('email').send_keys('   ')  # 좌측 괄호 안에 회원 아이디(이메일) 입력
    driver.find_element_by_name('password').send_keys('   ')  # 좌측 괄호 안에 회원 비밀번호 입력
    driver.find_element_by_class_name('css-11a3zmg').click()

    # 프로필 선택 화면 로딩이 완료될때까지 대기
    time.sleep(5)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1blyq76")))

    # 프로필 선택
    profiles = driver.find_elements_by_class_name('css-1blyq76')

    for profile in profiles:
        print(profile.text)
        if profile.text == "   ":  # 죄측 큰따옴표 안에 회원 프로필명 입력
            profile.click()

    time.sleep(5)
    driver.implicitly_wait(5)

    # ----------------------------------------------------------------------------------------------------------------------#

    # '보고싶어요' 에 있는 항목들 불러오기
    driver.get("https://watcha.com/wishes")

    time.sleep(5)
    driver.implicitly_wait(5)
    WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.CLASS_NAME, "css-1g3awd")))

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

    wishes = driver.find_elements_by_class_name('css-1g3awd')
    print("< Wishlist >")
    for wish in wishes:
        print(wish.text)
        result.append(wish.text)

    return result
