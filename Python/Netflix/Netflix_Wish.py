from Netflix_Login import driver
from selenium.webdriver.common.by import By   #DeprecationWarning 방지
import time

#!!execute just for login!!
#exec(open("Netflix_Login.py", encoding='UTF-8').read())
print("찜목록 불러오는 중...")

new_wish = "empty"
while new_wish.strip() != "":
    driver.get("https://www.netflix.com/browse/my-list")
    wishList = driver.find_elements(By.CLASS_NAME, 'fallback-text')
    time.sleep(1)
    #찜목록 출력
    print("        < 찜목록 >        ")
    print("------------------------------")
    for content in wishList:
        print(content.text)
    print("------------------------------")

    #찜목록 연동
    new_wish = input("찜목록에 추가할 컨텐츠를 입력해주세요.\n(delete : 중복 입력 시 찜 삭제, exit : 공백 입력 시 종료)\n컨텐츠 이름 : ")
    new_wish = new_wish.replace(' ', '')
    if new_wish.strip() == "":
        break

    #컨텐츠 검색
    url = "https://www.netflix.com/search?q=" + new_wish
    driver.get(url)
    searchResult = driver.find_element(By.CSS_SELECTOR, '#title-card-0-0 > div.ptrack-content > a > div.boxart-size-16x9.boxart-container.boxart-rounded > div > p')
    if searchResult.text.replace(' ', '') != new_wish.strip():
        print("해당 컨텐츠가 없습니다.")
        break
    print("찜목록 동기화...")
    driver.find_element(By.CSS_SELECTOR, "#row-0 > div > div > div > div > div > div.slider-item.slider-item-0").click()
    time.sleep(1)

    driver.find_elements(By.CLASS_NAME, "ltr-79elbk")[0].click()
    time.sleep(1)

driver.quit()