from selenium import webdriver
from selenium.webdriver.common.by import By   #DeprecationWarning 방지
import time
import getpass #비밀번호 입력 프롬프트



netflix_userId = input("넷플릭스 로그인 이메일 또는 전화번호를 입력해주세요 : ")
netflix_userPasswd = input("넷플릭스 비밀번호를 입력해주세요 : ") #!!getpass 오류!!
print("로그인 중...")

#driver load
options = webdriver.ChromeOptions()
options.add_argument('headless')
options.add_argument('window-size=1920x1080')
options.add_argument("disable-gpu")
driver = webdriver.Chrome(options=options)

driver.get('https://www.netflix.com/')
time.sleep(1)

#로그인 창 이동
login = driver.find_element(By.CSS_SELECTOR, '#appMountPoint > div > div > div > div > div > div.our-story-header-wrapper > div > a')
login.click()

#로그인 자동화
driver.find_element(By.NAME, 'userLoginId').send_keys(netflix_userId)
driver.find_element(By.NAME, 'password').send_keys(netflix_userPasswd)
driver.find_element(By.CSS_SELECTOR, '#appMountPoint > div > div.login-body > div > div > div.hybrid-login-form-main > form > button').click()

time.sleep(1)

#리스트 인덱스 방식
#driver.find_element(By.XPATH'//*[@id="appMountPoint"]/div/div/div[1]/div[1]/div[2]/div/div/ul/li[1]/div/a/div/div').click()
#driver.find_element(By.NAME, '//*[@id="appMountPoint"]/div/div/div[1]/div[1]/div[2]/div/div/ul/li[1]/div/a/div/div').click()
#!!형식 오류 InvalidSelectorException 발생!!

#리스트 출력 후 선택 방식
userProfiles = driver.find_elements(By.CLASS_NAME, 'profile-name')

print("        <프로필 리스트>        ")
print("------------------------------")
for profile in userProfiles:
    if profile.text != "프로필 추가":
        print(profile.text, end="  ")

print()
print("------------------------------")
netflix_userProfile = input("넷플릭스 프로필 이름을 입력해주세요(프로필 선택) : ")

for profile in userProfiles:
    if profile.text == netflix_userProfile:  # 죄측 큰따옴표 안에 회원 프로필명 입력
        profile.click()
        break
time.sleep(2)

####로그인만 테스트 해볼 때 해제해주세요.#####
#driver.quit()
