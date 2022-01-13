#Import
import selenium
import time

from selenium import webdriver
from selenium.webdriver import ActionChains

from selenium.webdriver.common.keys import Keys
from selenium.webdriver.common.by import By

from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support.ui import WebDriverWait

#Wavve 크롬 켜기
URL = 'https://www.wavve.com/'

driver = webdriver.Chrome(executable_path = 'chromedriver')
driver.get(url=URL)
time.sleep(1)
driver.maximize_window()

#팝업창 끄기
posting = driver.find_element_by_xpath('//*[@id="contents"]/div[1]/section/div[1]/a/img')
posting.click()

#로그인 화면
login = driver.find_element_by_xpath('//*[@id="app"]/div[1]/div[2]/header/div[1]/div[2]/div/ul/li[1]/a')
login.click()


#skt 로그인 화면
driver.execute_script('window.scrollTo(0,600);')
skt_login = driver.find_element_by_xpath('//*[@id="app"]/div[1]/main/div/div[2]/div[2]/ul/li[2]/a/span[1]')
skt_login.click()
time.sleep(2)

#로그인 액션체인
id_box = driver.find_element_by_css_selector('#userId')
passwd_box = driver.find_element_by_css_selector('#password')
login_button = driver.find_element_by_css_selector('#content > div:nth-child(3) > div.content-bottom > div:nth-child(1)')

login_act = ActionChains(driver)

login_act.send_keys_to_element(id_box,'T멤버쉽 아이디').send_keys_to_element(passwd_box,'비밀번호').click(login_button).perform()
time.sleep(2)

#프로필 선택
profile = driver.find_element_by_xpath('//*[@id="app"]/div[1]/div/div[2]/div/div[1]/a[1]/div/img')
profile.click()
time.sleep(1)

#시청중인 VOD 페이지 이동
driver.get(url = 'https://www.wavve.com/my/use_list_vod_history')

#VOD 페이지 수
driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
vod_page = driver.find_elements_by_class_name('paging-type02')
for i in vod_page:
    page = i.text

#시청중인 VOD 목록 출력
for k in page:
    watching_list_vod = driver.find_elements_by_class_name('con-tit')
    for j in watching_list_vod:
        vod = j.text
        print(vod)
    page_URL = 'https://www.wavve.com/my/use_list_vod_history?page=' + str(k)
    driver.get(url=page_URL)
    time.sleep(1)

#시청중인 영화 페이지 이동
driver.get(url = 'https://www.wavve.com/my/use_list_movie_history')

#시청중인 영화 페이지 수
driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
watching_movie_page = driver.find_elements_by_class_name('paging-type02')
for i in watching_movie_page:
    page = i.text

#시청중인 영화 목록 출력
for k in page:
    watching_list_movie = driver.find_elements_by_class_name('con-tit')
    for j in watching_list_movie:
        movie = j.text
        print(movie)
    page_URL = 'https://www.wavve.com/my/use_list_movie_history?page=' + str(k)
    driver.get(url=page_URL)
    time.sleep(1)

#찜한 영화 페이지 이동
driver.get(url = 'https://www.wavve.com/my/like_movie')

#찜한 영화 페이지 수
driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
jjim_page = driver.find_elements_by_class_name('paging-type02')
for i in jjim_page:
    page = i.text

#찜한 영화 목록 출력
for k in page:
    wish_list_movie = driver.find_elements_by_class_name('con-tit')
    for j in wish_list_movie:
        wish_movie = j.text
        print(wish_movie)
    page_URL = 'https://www.wavve.com/my/like_movie?page=' + str(k)
    driver.get(url=page_URL)
    time.sleep(1)