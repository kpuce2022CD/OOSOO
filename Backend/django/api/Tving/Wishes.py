from selenium import webdriver
from pyvirtualdisplay import Display
from api.Tving.Login import t_login
import time

def t_wishes(email, pwd, name):
    # display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    # display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)
    driver.maximize_window()

    #티빙 로그인
    t_login(email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    #<wishlist>
    result2 = list()

    # 찜 VOD
    driver.get('https://www.tving.com/my/favorite?tab=program')

    wishlist_vods = driver.find_elements_by_class_name('item__info')
    for vod in wishlist_vods:
        result2.append(vod.text)
    time.sleep(2)
    driver.implicitly_wait(5)

    # 찜 영화
    driver.get('https://www.tving.com/my/favorite?tab=movie')

    wishlist_movies = driver.find_elements_by_class_name('item__info')
    for vod in wishlist_movies:
        result2.append(vod.text)
    time.sleep(2)
    driver.implicitly_wait(5)

    driver.quit()
    # display.stop()

    return result2