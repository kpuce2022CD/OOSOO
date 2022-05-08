# selenium 불러오기
from selenium import webdriver
from pyvirtualdisplay import Display
from api.Tving.Login import t_login
import time

def t_watchings(email, pwd, name):
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

    result = list()
    title_vod = ""

    # 시청중인 VOD 페이지 이동
    driver.get('https://www.tving.com/my/watch?tab=episode')

    # 시청중인 VOD
    watching_vods = driver.find_elements_by_class_name('item__title')
    for vod in watching_vods:
        title = vod.text
        title_split = title.split(' ')
        for t in range(0, len(title_split) - 1):
            title_vod = title_vod + title_split[t] + " "

        result.append(title_vod[:-1])
        title_vod = ""

    time.sleep(2)
    driver.implicitly_wait(5)

    # 시청중인 영화 페이지 이동
    driver.get('https://www.tving.com/my/watch?tab=movie')

    # 시청중인 영화
    watching_movies = driver.find_elements_by_class_name('item__title')
    for vod in watching_movies:
        result.append(vod.text)
    time.sleep(2)
    driver.implicitly_wait(5)

    driver.quit()
    # display.stop()

    return result