# selenium 불러오기
from selenium import webdriver
from pyvirtualdisplay import Display
from api.Wavve.Login import wav_login
import time


def wav_addwish(email, pwd, name, title):
    display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"    # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)
    driver.maximize_window()

    # 웨이브 로그인
    wav_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    # 영화 검색
    url = "https://www.wavve.com/search/search?category=movie&searchWord=" + str(title)
    driver.get(url)

    time.sleep(2)
    driver.implicitly_wait(5)

    # 팝업창 닫기
    btn_x = driver.find_element_by_xpath('//*[@id="contents"]/div[1]/div/a/img')
    btn_x.click()

    time.sleep(2)
    driver.implicitly_wait(5)

    # 첫 번째 검색 결과 선택
    movie = driver.find_element_by_xpath('//*[@id="contents"]/div[3]/div/div/div/div[2]/div[1]/a/div[2]/div/div/span/span')
    movie.click()

    time.sleep(2)
    driver.implicitly_wait(5)

    # 찜하기 버튼 클릭
    btn_wish = driver.find_element_by_xpath('//*[@id="contents"]/section/div/div[2]/div[2]/div/ul/li[2]')
    btn_wish.click()

    time.sleep(2)

    # 경고창 제거
    alert = driver.switch_to.alert
    alert.accept()

    time.sleep(2)

    driver.quit()
    display.stop()

    return "success"