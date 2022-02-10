# selenium 불러오기
from selenium import webdriver
from pyvirtualdisplay import Display
from api.Wavve.Login import wav_login
import time


def wav_watchings(login_way, email, pwd, name):
    display = Display(visible=0, size=(1920, 1080))  # PyCharm 테스트시 주석처리
    display.start()  # PyCharm 테스트시 주석처리

    # chrome창(웹드라이버) 열기  (AWS 경로 : "/home/ubuntu/django_server/chromedriver")
    driver = webdriver.Chrome("/home/ubuntu/django_server/chromedriver")  # PyCharm 테스트시  r"D:\2022 Capston\OOSOO\Python\Watcha\chromedriver.exe"

    # 웨이브 로그인
    wav_login(login_way, email, pwd, name, driver)

    # ----------------------------------------------------------------------------------------------------------------------#

    result = list()

    # 시청중인 VOD 페이지 이동
    driver.get('https://www.wavve.com/my/use_list_vod_history')

    # VOD 페이지 수
    driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
    vod_page = driver.find_elements_by_class_name('paging-type02')
    for i in vod_page:
        page = i.text

    # 시청중인 VOD 목록
    for k in page:
        page_URL = 'https://www.wavve.com/my/use_list_vod_history?page=' + str(k)
        watching_vods = driver.find_elements_by_class_name('con-tit')
        for vod in watching_vods:
            print(vod.text)
            result.append(vod.text)
        driver.get(page_URL)
        time.sleep(2)
        driver.implicitly_wait(5)

    # 시청중인 영화 페이지 이동
    driver.get('https://www.wavve.com/my/use_list_movie_history')

    # 시청중인 영화 페이지 수
    driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
    watching_movie_page = driver.find_elements_by_class_name('paging-type02')
    for i in watching_movie_page:
        page = i.text

    # 시청중인 영화 목록
    for k in page:
        page_URL = 'https://www.wavve.com/my/use_list_movie_history?page=' + str(k)
        watching_movies = driver.find_elements_by_class_name('con-tit')
        for movie in watching_movies:
            print(movie.text)
            result.append(movie.text)
        driver.get(page_URL)
        time.sleep(2)
        driver.implicitly_wait(5)

    driver.quit()
    display.stop()

    return result
