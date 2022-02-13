# selenium 불러오기
from selenium import webdriver
from pyvirtualdisplay import Display
from api.Wavve.Login import wav_login
import time


def wav_watchings(email, pwd, name):
    #display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    #display.start()  # PyCharm 테스트시 주석처리

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

    result = list()
    page = list()

    # 시청중인 VOD 페이지 이동
    driver.get('https://www.wavve.com/my/use_list_vod_history')

    # VOD 페이지 수
    vod_page = driver.find_elements_by_css_selector('#contents > div.mypooq-inner-wrap > section > div > div > div.paging-type02 > a')
    for i in vod_page:
        page.append(i.text)

    # 시청중인 VOD 목록
    for k in page:
        page_URL = 'https://www.wavve.com/my/use_list_vod_history?page=' + str(k)
        watching_vods = driver.find_elements_by_class_name('con-tit')
        for vod in watching_vods:
            result.append(vod.text)
        driver.get(page_URL)
        time.sleep(2)
        driver.implicitly_wait(5)

    # 시청중인 영화 페이지 이동
    driver.get('https://www.wavve.com/my/use_list_movie_history')

    # 시청중인 영화 페이지 수
    watching_movie_page = driver.find_elements_by_css_selector('#contents > div.mypooq-inner-wrap > section > div > div > div.paging-type02 > a')
    page.clear()
    for i in watching_movie_page:
        page.append(i.text)

    # 시청중인 영화 목록
    for k in page:
        page_URL = 'https://www.wavve.com/my/use_list_movie_history?page=' + str(k)
        watching_movies = driver.find_elements_by_class_name('con-tit')
        for movie in watching_movies:
            result.append(movie.text)
        driver.get(page_URL)
        time.sleep(2)
        driver.implicitly_wait(5)

    driver.quit()
    #display.stop()

    return result
