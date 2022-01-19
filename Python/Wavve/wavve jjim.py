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
driver.implicitly_wait(10)
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


#검색하기 버튼
btn_search = driver.find_element_by_xpath('//*[@id="app"]/div[1]/div[2]/header/div[2]/div/button')
btn_search.click()

query_txt = input('검색할 영화 제목은 무엇입니까?:')
# 사용자에게 검색어 입력


element = driver.find_element_by_id("search-ip")
element.send_keys(query_txt)
time.sleep(1)
element.send_keys("\n")
#검색창의 이름을 찾아서 검색어를 입력

#팝업창 닫기
btn_x = driver.find_element_by_xpath('//*[@id="contents"]/div[1]/div/a/img')
btn_x.click()

#영화 카테고리 선택
btn_movie = driver.find_element_by_xpath('//*[@id="contents"]/div[2]/div/ul/li[4]/button')
btn_movie.click()

#첫 번째 검색 결과 선택
search_keyword = driver.find_element_by_xpath('//*[@id="contents"]/div[3]/div/div/div/div[2]/div[1]/a/div[2]/div/div/span/span')
search_keyword.click()

#찜하기 버튼 클릭
btn_jjim = driver.find_element_by_xpath('//*[@id="contents"]/section/div/div[2]/div[2]/div/ul/li[2]')
btn_jjim.click()

#경고창 제거
alert = driver.switch_to.alert
alert.accept()