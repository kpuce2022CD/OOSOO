import time
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from Disney_Login import driver
from selenium.webdriver.common.by import By   #DeprecationWarning 방지

#!!execute just for login!!
#exec(open("Disney_Login.py", encoding='UTF-8').read())
print("찜목록 추가/삭제 컨텐츠 입력 : ")
content = input().replace(' ','').strip()
driver.get("https://www.disneyplus.com/search")

search_box = WebDriverWait(driver, 16).until(EC.presence_of_element_located((By.ID, "search-input")))
search_box.send_keys(content)

# 페이지 스크롤
last_height = driver.execute_script("return document.body.scrollHeight")

while True:
    scroll_down = 0
    while scroll_down < 10:
        driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        time.sleep(0.2)
        scroll_down += 1

    # 스크롤 내린 후 스크롤 높이 다시 가져옴
    new_height = driver.execute_script("return document.body.scrollHeight")
    if new_height == last_height:
        break
    last_height = new_height

try:
    WebDriverWait(driver, 10).until(EC.presence_of_element_located((By.XPATH, '//*[@id="section_index"]/div/div/div[2]/section/div/div')))
    driver.find_element(By.XPATH, '//*[@data-testid="search-result-0"]').send_keys(Keys.ENTER)

    driver.implicitly_wait(10)
    driver.find_element(By.XPATH,'//*[@id="details_index"]/div/article/div[3]/div/div[2]/div/span/button').click()
except:
    print("페이지 로드중에 오류가 발생했습니다.")
finally:
    print("찜 추가/삭제 완료.")
    time.sleep(3)      #디즈니는 버튼을 누르고 강제로 기다려야함 - 위시리스트 추가/삭제 동작이 프론트(웹)에서 진행되는 듯

driver.quit()