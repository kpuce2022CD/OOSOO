# selenium 불러오기
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time
import asyncio

from api.models import UserInterworking
from api.Netflix.Login import n_login
from api.DisneyPlus.Login import d_login
from api.Tving.Login import t_login
from api.Watcha.Login_2 import wat_login
from api.Wavve.Login import wav_login

def netflix_url(title, driver): #제목으로 netflix에 있는 해당 컨텐츠 시청 url 리턴
    url_home = "https://www.netflix.com/kr/"
    driver.get(url_home)

    URL = "https://www.netflix.com/search?q=" + title
    driver.get(URL)

    first_content = driver.find_element(By.XPATH, '//*[@id="row-0"]/div/div/div/div/div/div[1]')
    first_content.click()

    btn_watch = driver.find_element(By.XPATH, '//*[@id="appMountPoint"]/div/div/div[1]/div[2]/div/div[1]/div[4]/div/div[1]/div[1]/a/button')
    btn_watch.click()

    time.sleep(2)
    return driver.current_url


def disney_url(title, driver): #제목으로 disney plus에 있는 해당 컨텐츠 시청 url 리턴
    driver.get('https://www.disneyplus.com/ko-kr/home/')
    time.sleep(2)
    driver.implicitly_wait(5)

    btn_search = driver.find_element(By.XPATH, '//*[@id="nav-list"]/span[2]')
    btn_search.click()

    input_bar = driver.find_element(By.XPATH, '//*[@id="search-input"]')
    input_bar.send_keys(title)

    content = driver.find_element(By.XPATH, '//*[@id="section_index"]/div/div/div[2]/section/div/div/div[1]/a')
    content.click()
    time.sleep(2)
    driver.implicitly_wait(5)

    #시청 버튼 예외 처리 - 해당 콘텐츠 디테일의 줄 수에 따라 버튼의 XPATH가 바뀜
    try:
        btn_watch = driver.find_element(By.XPATH, '//*[@id="details_index"]/div/article/div[3]/div/div[2]/button[1]')
    except:
        btn_watch = driver.find_element(By.XPATH, '//*[@id="details_index"]/div/article/div[3]/div/div[3]/button')

    btn_watch.click()
    time.sleep(2)
    driver.implicitly_wait(5)

    return driver.current_url


def wavve_url(title, driver): #제목으로 wavve에 있는 해당 컨텐츠 시청 url 리턴

    xbox = driver.find_element(By.XPATH, '//*[@id="contents"]/div[1]/section/div[1]')
    xbox.click()

    btn_search = driver.find_element(By.XPATH, '//*[@id="app"]/div[1]/div[2]/header/div[2]/div/button')
    btn_search.click()

    input_title = driver.find_element(By.XPATH, '//*[@id="search-ip"]')
    input_title.send_keys(title)
    input_title.send_keys(Keys.RETURN)
    time.sleep(2)
    driver.implicitly_wait(5)

    xbox2 = driver.find_element(By.XPATH, '//*[@id="contents"]/div[1]/div/a')
    xbox2.click()

    first_content = driver.find_element(By.XPATH, '//*[@id="multisection_index_undefined"]/div[2]/div[1]/div[1]/div')
    first_content.click()
    time.sleep(2)
    driver.implicitly_wait(5)

    return driver.current_url


def watcha_url(title, driver, c_type): #제목으로 watcha에 있는 해당 컨텐츠 시청 url 리턴
    ##Content = TV일 때
    if c_type == "tv":
        tv_search_url = "https://watcha.com/browse/search?query=" + title + "&filter=tv_search"
        driver.get(tv_search_url)
        time.sleep(5)
        driver.implicitly_wait(5)

        tv_content = driver.find_element(By.XPATH, '//*[@id="root"]/div[1]/main/div[1]/section/div[1]/ul/li[1]/article[1]/a')
        tv_content.click()
        time.sleep(5)
        driver.implicitly_wait(5)

    ##Content = Movie일 때
    elif c_type == "movie":
        movie_search_url = "https://watcha.com/browse/search?query=" + title + "&filter=movie_search"
        driver.get(movie_search_url)
        time.sleep(5)
        driver.implicitly_wait(5)

        movie_content = driver.find_element(By.XPATH, '//*[@id="root"]/div[1]/main/div[1]/section/div/ul/li[1]/article[1]/a')
        movie_content.click()
        time.sleep(5)
        driver.implicitly_wait(5)

    else:
        return "error"

    time.sleep(2)
    driver.implicitly_wait(5)

    btn_watch = driver.find_element(By.XPATH, '//*[@id="root"]/div[1]/main/div[1]/header[1]/div/div/section/div[1]/a')
    btn_watch.click()

    time.sleep(2)
    driver.implicitly_wait(5)

    driver.save_screenshot("screenshot_ott_link_watcha.png")
    return driver.current_url

def tving_url(title, driver, c_type): #제목으로 tving에 있는 해당 컨텐츠 시청
                                      # url 리턴 제목서칭이 이상함!!!!!!!!
    if c_type == "tv":
        search_url = "https://www.tving.com/search/tv?keyword=" + title
        driver.get(search_url)
        time.sleep(2)
        driver.implicitly_wait(5)
        driver.save_screenshot("test_tv1.png")

    elif c_type == "movie":
        search_url = "https://www.tving.com/search/movie?keyword=" + title
        driver.get(search_url)
        time.sleep(2)
        driver.implicitly_wait(5)
        driver.save_screenshot("test_movie1.png")

    else:
        return "error"

    first_content = driver.find_element(By.XPATH, '//*[@id="__next"]/main/section/div/div/section/section/div[2]/div[1]')
    first_content.click()
    time.sleep(2)
    driver.implicitly_wait(5)

    driver.save_screenshot("test5.png")

    btn_watch = driver.find_element(By.XPATH, '//*[@id="__next"]/main/section/article/article/div[2]/div[2]/div[2]/button')
    btn_watch.click()
    time.sleep(2)
    driver.implicitly_wait(5)
    driver.save_screenshot("test6.png")

    return driver.current_url


def ott_link(email, title, c_type, platform):

    # chrome창(웹드라이버) 열기  (Docker 경로 : "/webserver/chromedriver")
    path = "/webserver/chromedriver"
    options = webdriver.ChromeOptions()
    options.add_argument('--headless')
    options.add_argument('--no-sandbox')
    options.add_argument('disable-gpu')
    options.add_argument("--window-size=1920,1080")
    options.add_argument('--disable-dev-shm-usage')
    driver = webdriver.Chrome(path, chrome_options=options)

    user_interworking = dict()

    interworking = UserInterworking.objects.values().filter(u_email=email)

    for i in interworking:
        if i['platform'] == platform:
            user_interworking = i
            break

    if platform == "netflix":
        n_login(i['id'], i['passwd'], i['profile_name'], driver)

        link = netflix_url(title, driver)
        return link

    elif platform == "disney":
        d_login(i['id'], i['passwd'], i['profile_name'], driver)
        link = disney_url(title, driver)
        return link

    elif platform == "wavve":
        wav_login(i['id'], i['passwd'], i['profile_name'], driver)
        link = wavve_url(title, driver)
        return link

    elif platform == "watcha":
        wat_login(i['id'], i['passwd'], i['profile_name'], driver)
        #await asyncio.wait([w_login(i['id'], i['passwd'], i['profile_name'], driver)])
        link = watcha_url(title, driver, c_type)
        return link

    elif platform == "tving":
        t_login(i['id'], i['passwd'], i['profile_name'], driver)
        link = tving_url(title, driver, c_type)
        return link

    driver.quit()