# selenium 불러오기
from selenium import webdriver
from pyvirtualdisplay import Display
from api.Wavve.Login import wav_login
import time


def wav_wishes(login_way,email, pwd, name):
    display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"    # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    driver = webdriver.Chrome(path, chrome_options=options)
    driver.maximize_window()

    # 웨이브 로그인
    wav_login(login_way, email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    result = list()

    # 찜한 영화 페이지 이동
    driver.get('https://www.wavve.com/my/like_movie')

    # 찜한 영화 페이지 수
    driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
    jjim_page = driver.find_elements_by_class_name('paging-type02')
    for i in jjim_page:
        page = i.text

    # 찜한 영화 목록 출력
    for k in page:
        page_URL = 'https://www.wavve.com/my/like_movie?page=' + str(k)
        wishes = driver.find_elements_by_class_name('con-tit')
        for wish in wishes:
            print(wish.text)
            result.append(wish.text)
        driver.get(page_URL)
        time.sleep(2)
        driver.implicitly_wait(5)

    driver.quit()
    display.stop()

    return result
