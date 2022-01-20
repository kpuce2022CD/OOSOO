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
driver.implicitly_wait(3)
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

#로그인 액션체인
id_box = driver.find_element_by_css_selector('#userId')
passwd_box = driver.find_element_by_css_selector('#password')
login_button = driver.find_element_by_css_selector('#content > div:nth-child(3) > div.content-bottom > div:nth-child(1)')

login_act = ActionChains(driver)

login_act.send_keys_to_element(id_box,'T멤버쉽 아이디').send_keys_to_element(passwd_box,'비밀번호').click(login_button).perform()

#프로필 선택
profile = driver.find_element_by_xpath('//*[@id="app"]/div[1]/div/div[2]/div/div[1]/a[1]/div/img')
profile.click()

##시청중인 콘텐츠 - VOD일 경우

#VOD-1 시청중인 VOD 페이지 이동
driver.get(url = 'https://www.wavve.com/my/use_list_vod_history')

#VOD-2 페이지 수
driver.execute_script('window.scrollTo(0, document.body.scrollHeight);')
vod_page = driver.find_elements_by_class_name('paging-type02')
for i in vod_page:
    page = i.text

#VOD-3 시청중인 목록에 요청한 콘텐츠가 있으면 이어보기
query_text = input('vod 이름 입력: ')

for k in page:
    page_URL_vod = 'https://www.wavve.com/my/use_list_vod_history?page=' + str(k)
    driver.get(url = page_URL_vod)
    try:
        vod_title = driver.find_element_by_partial_link_text(query_text)
        if vod_title != 0:
            print(vod_title)
            vod_title.click()
            watching_vod_val = 1
            break;
    except:
        watching_vod_val = 0

##SEARCH-VOD
#1 시청중인 VOD 목록에 없을 경우 검색
if watching_vod_val == 0:
    driver.get(url = 'https://www.wavve.com')
    btn_search = driver.find_element_by_xpath('//*[@id="app"]/div[1]/div[2]/header/div[2]/div/button')
    btn_search.click()

##SEARCH-VOD
#2 검색창의 이름을 찾아서 검색어를 입력
    element = driver.find_element_by_id("search-ip")
    element.send_keys(query_text)
    time.sleep(1)
    element.send_keys("\n")

##SEARCH-VOD
#3 팝업창 닫기
    btn_x = driver.find_element_by_xpath('//*[@id="contents"]/div[1]/div/a/img')
    btn_x.click()

##SEARCH-VOD
#4 첫 번째 검색된 콘텐츠 선택
    btn_vod = driver.find_element_by_xpath('//*[@id="multisection_index_undefined"]/div[2]/div[1]/div[1]/div/a/div[1]/img')
    btn_vod.click()