# selenium 불러오기
from selenium import webdriver
from pyvirtualdisplay import Display
from api.Tving.Login import t_login
import time

def t_addwish(email, pwd, name, title, type):
    display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)
    driver.maximize_window()

    # 웨이브 로그인
    t_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    if type == 'movie':
        url = "https://www.tving.com/search/movie?keyword=" + str(title)
    else:
        url = "https://www.tving.com/search/tv?keyword=" + str(title)

    driver.get(url)
    time.sleep(2)
    driver.implicitly_wait(5)

    # 첫 번째 검색 결과 선택
    btn_add = driver.find_element_by_xpath('//*[@id="__next"]/main/section/div/div/section/section/div[2]/div[1]')
    btn_add.click()

    time.sleep(2)
    driver.implicitly_wait(5)

    btn_wish = driver.find_element_by_xpath(
        '//*[@id="__next"]/main/section/article/article/div[2]/div[2]/div[2]/div/button')
    btn_wish.click()
    time.sleep(2)

    return "success"