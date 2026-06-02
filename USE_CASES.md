# Use Cases - Homestay Booking

Tai lieu nay tong hop cac use case dang duoc the hien trong source code, public module API, UI Swing va database schema cua project Homestay Booking.

## 1. Pham Vi He Thong

Homestay Booking la ung dung desktop Java Swing cho phep nguoi dung tim homestay/phong, dat phong, thanh toan bang vi noi bo, huy booking, danh gia phong; cho phep chu homestay quan ly homestay/phong/gia/lich khoa/booking; va cho phep admin quan ly nguoi dung, tien nghi, voucher, hoa don.

## 2. Tac Nhan

| Tac nhan | Mo ta |
| --- | --- |
| Guest | Nguoi chua dang nhap. Co the xem danh sach homestay/phong, dang ky, dang nhap. |
| Customer | Nguoi dung da dang nhap voi role `CUSTOMER`. Co the cap nhat ho so, tao homestay, dat phong, nap vi, xem booking va danh gia booking da hoan tat. |
| Owner | Nguoi dung role `OWNER`. Co the quan ly homestay/phong cua minh, gia, lich khoa, booking cua cac phong minh so huu, nhan doanh thu. |
| Admin | Nguoi dung role `ADMIN`. Co quyen quan tri nguoi dung, voucher, tien nghi, hoa don va co the thay chu so huu thao tac nhieu tai nguyen. |
| He thong | Cac module noi bo thuc hien tinh gia, ap voucher, tru/hoan vi, sinh hoa don, doi trang thai booking. |

## 3. Trang Thai Va Rang Buoc Chinh

| Doi tuong | Trang thai/Gia tri |
| --- | --- |
| User role | `ADMIN`, `CUSTOMER`, `OWNER` |
| User status | `ACTIVE`, `LOCKED` |
| Homestay status | `ACTIVE`, `INACTIVE`, `DELETED` |
| Room status | `AVAILABLE`, `INACTIVE`, `MAINTENANCE` |
| Booking status | `PENDING_PAYMENT`, `CONFIRMED`, `CANCELLED`, `COMPLETED` |
| Voucher status | `ACTIVE`, `INACTIVE`, `EXPIRED` |
| Discount type | `FIXED_AMOUNT`, `PERCENTAGE` |
| Payment method | `WALLET` |
| Payment status | `UNPAID`, `PAID`, `REFUNDED`, `FAILED` |
| Settlement status | `NOT_SETTLED`, `SETTLED`, `CANCELLED` |
| Wallet transaction type | `DEPOSIT`, `PAYMENT`, `REFUND`, `OWNER_REVENUE`, `WITHDRAW` |

Quy tac chung:

- Cac thao tac yeu cau tai khoan dang nhap se loi neu chua dang nhap.
- Tai khoan `LOCKED` khong duoc dang nhap.
- Email phai hop le va khong trung.
- So du vi khong duoc am.
- Khoang ngay booking/block phai co ngay bat dau truoc ngay ket thuc.
- Gia moi dem va suc chua phong phai lon hon 0.
- Rating danh gia nam trong khoang 1 den 5.

## 4. Use Case Tong Quan

| Ma | Ten use case | Tac nhan chinh |
| --- | --- | --- |
| UC-IAM-01 | Dang ky tai khoan | Guest |
| UC-IAM-02 | Dang nhap | Guest |
| UC-IAM-03 | Dang xuat | Customer, Owner, Admin |
| UC-IAM-04 | Xem ho so ca nhan | Customer, Owner, Admin |
| UC-IAM-05 | Cap nhat ho so ca nhan | Customer, Owner, Admin |
| UC-IAM-06 | Doi mat khau | Customer, Owner, Admin |
| UC-IAM-07 | Xem danh sach nguoi dung | Admin |
| UC-IAM-08 | Xem chi tiet nguoi dung | Admin |
| UC-IAM-09 | Cap nhat ho so nguoi dung | Admin |
| UC-IAM-10 | Khoa tai khoan | Admin |
| UC-IAM-11 | Mo khoa tai khoan | Admin |
| UC-IAM-12 | Nang nguoi dung thanh Owner | Admin/He thong |
| UC-CAT-01 | Xem danh sach homestay | Guest, Customer, Owner, Admin |
| UC-CAT-02 | Xem chi tiet homestay | Guest, Customer, Owner, Admin |
| UC-CAT-03 | Tao homestay | Customer, Owner, Admin |
| UC-CAT-04 | Quan ly homestay cua toi | Owner, Customer |
| UC-CAT-05 | Cap nhat homestay | Owner, Admin |
| UC-CAT-06 | Xoa homestay | Owner, Admin |
| UC-CAT-07 | Xem danh sach phong | Guest, Customer, Owner, Admin |
| UC-CAT-08 | Xem chi tiet phong | Guest, Customer, Owner, Admin |
| UC-CAT-09 | Tao phong | Owner, Admin |
| UC-CAT-10 | Cap nhat phong | Owner, Admin |
| UC-CAT-11 | Xoa phong | Owner, Admin |
| UC-CAT-12 | Quan ly tien nghi | Admin |
| UC-CAT-13 | Gan/Go tien nghi cho phong | Owner, Admin |
| UC-CAT-14 | Xem danh gia phong | Guest, Customer, Owner, Admin |
| UC-CAT-15 | Tao danh gia | Customer |
| UC-CAT-16 | Cap nhat danh gia | Customer/He thong |
| UC-INV-01 | Tao lich gia phong | Owner, Admin |
| UC-INV-02 | Cap nhat lich gia phong | Owner, Admin |
| UC-INV-03 | Xoa lich gia phong | Owner, Admin |
| UC-INV-04 | Xem lich gia phong | Owner, Admin, He thong |
| UC-INV-05 | Tinh gia phong | He thong |
| UC-INV-06 | Khoa ngay phong | Owner, Admin |
| UC-INV-07 | Mo khoa ngay phong | Owner, Admin |
| UC-INV-08 | Xem ngay bi khoa cua phong | Owner, Admin, He thong |
| UC-RES-01 | Tao booking | Customer, Admin |
| UC-RES-02 | Xem booking cua toi | Customer |
| UC-RES-03 | Xem chi tiet booking | Customer, Owner, Admin |
| UC-RES-04 | Xem booking cua homestay toi | Owner |
| UC-RES-05 | Xem truoc tien hoan khi huy | Customer |
| UC-RES-06 | Huy booking | Customer |
| UC-RES-07 | Hoan tat booking | Owner, Admin |
| UC-RES-08 | Kiem tra quyen danh gia booking | Customer |
| UC-COM-01 | Tao voucher | Admin |
| UC-COM-02 | Cap nhat voucher | Admin |
| UC-COM-03 | Vo hieu hoa voucher | Admin |
| UC-COM-04 | Xem danh sach voucher | Admin |
| UC-COM-05 | Tinh giam gia voucher | He thong |
| UC-COM-06 | Xem so du vi | Customer, Owner, Admin |
| UC-COM-07 | Xem lich su giao dich vi | Customer, Owner, Admin |
| UC-COM-08 | Nap tien vao vi | Customer, Owner, Admin |
| UC-COM-09 | Thanh toan booking bang vi | He thong |
| UC-COM-10 | Hoan tien booking | He thong |
| UC-COM-11 | Quyet toan doanh thu cho owner | Owner, Admin, He thong |
| UC-COM-12 | Xem hoa don theo booking | Admin, Customer, Owner |

## 5. Nhom IAM - Tai Khoan Va Phan Quyen

### UC-IAM-01 - Dang ky tai khoan

**Tac nhan chinh:** Guest

**Muc tieu:** Tao tai khoan moi de su dung he thong.

**Tien dieu kien:** Guest chua can dang nhap.

**Du lieu vao:** Ho ten, email, mat khau, so dien thoai, ngay sinh.

**Luong chinh:**

1. Guest mo man hinh dang ky.
2. Guest nhap thong tin tai khoan.
3. He thong kiem tra ho ten/email/mat khau.
4. He thong kiem tra email chua ton tai.
5. He thong ma hoa mat khau.
6. He thong tao user moi voi role `CUSTOMER`, status `ACTIVE`, balance mac dinh.
7. He thong thong bao dang ky thanh cong.

**Ngoai le:**

- Ho ten trong, email sai dinh dang hoac mat khau duoi 6 ky tu: bao loi validation.
- Email da ton tai: bao loi trung email.

**Hau dieu kien:** Tai khoan moi duoc luu trong bang `users`.

### UC-IAM-02 - Dang nhap

**Tac nhan chinh:** Guest

**Muc tieu:** Xac thuc va vao he thong theo dung role.

**Tien dieu kien:** Tai khoan da ton tai va dang `ACTIVE`.

**Du lieu vao:** Email, mat khau.

**Luong chinh:**

1. Guest mo dialog dang nhap.
2. Guest nhap email va mat khau.
3. He thong validate email/mat khau.
4. He thong tim user theo email.
5. He thong doi chieu mat khau.
6. He thong kiem tra status user la `ACTIVE`.
7. He thong luu current user va refresh man hinh chinh theo role.

**Ngoai le:**

- Email/mat khau khong dung: bao loi dang nhap.
- Tai khoan `LOCKED`: khong cho dang nhap.

**Hau dieu kien:** Session hien tai co `currentUser`.

### UC-IAM-03 - Dang xuat

**Tac nhan chinh:** Customer, Owner, Admin

**Muc tieu:** Ket thuc phien dang nhap.

**Luong chinh:**

1. Nguoi dung chon dang xuat.
2. He thong xoa `currentUser`.
3. He thong dua giao dien ve trang thai Guest.

**Hau dieu kien:** Khong con nguoi dung dang nhap.

### UC-IAM-04 - Xem ho so ca nhan

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Luong chinh:**

1. Nguoi dung mo man hinh ho so.
2. He thong lay thong tin user hien tai.
3. He thong hien thi ID, ho ten, email, phone, dob, role.

**Ngoai le:** Chua dang nhap: bao loi yeu cau dang nhap.

### UC-IAM-05 - Cap nhat ho so ca nhan

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Du lieu vao:** Ho ten, email, phone, dob.

**Luong chinh:**

1. Nguoi dung mo ho so ca nhan.
2. Nguoi dung chinh sua thong tin.
3. He thong validate ho ten va email.
4. He thong kiem tra email moi khong trung voi user khac.
5. He thong cap nhat user hien tai.
6. He thong refresh current user.

**Ngoai le:**

- Ho ten trong/email sai dinh dang: bao loi validation.
- Email da duoc user khac dung: bao loi trung email.

### UC-IAM-06 - Doi mat khau

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Du lieu vao:** Mat khau cu, mat khau moi.

**Luong chinh:**

1. Nguoi dung mo man hinh doi mat khau.
2. Nguoi dung nhap mat khau cu va moi.
3. He thong kiem tra current user.
4. He thong doi chieu mat khau cu.
5. He thong validate mat khau moi toi thieu 6 ky tu.
6. He thong ma hoa va cap nhat mat khau moi.

**Ngoai le:**

- Mat khau cu sai: bao loi.
- Mat khau moi khong hop le: bao loi validation.

### UC-IAM-07 - Xem danh sach nguoi dung

**Tac nhan chinh:** Admin

**Tien dieu kien:** Da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin mo man hinh quan ly user.
2. Admin co the nhap tu khoa tim kiem.
3. He thong kiem tra quyen admin.
4. He thong tra danh sach user gom ho ten, email, phone, role, status.

**Ngoai le:** Khong phai admin: tu choi quyen.

### UC-IAM-08 - Xem chi tiet nguoi dung

**Tac nhan chinh:** Admin

**Tien dieu kien:** Da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin chon mot user.
2. He thong kiem tra quyen admin.
3. He thong lay chi tiet user gom profile, role, status, balance.
4. He thong hien thi chi tiet.

**Ngoai le:** User khong ton tai: bao khong tim thay.

### UC-IAM-09 - Cap nhat ho so nguoi dung

**Tac nhan chinh:** Admin

**Tien dieu kien:** Da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin chon user can sua.
2. Admin nhap ho ten, email, phone, dob.
3. He thong kiem tra quyen admin.
4. He thong validate du lieu.
5. He thong kiem tra email khong trung.
6. He thong cap nhat user.

**Ngoai le:** Khong co quyen, user khong ton tai, email trung, du lieu khong hop le.

### UC-IAM-10 - Khoa tai khoan

**Tac nhan chinh:** Admin

**Tien dieu kien:** Da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin chon user can khoa.
2. He thong kiem tra quyen admin.
3. He thong cap nhat status user thanh `LOCKED`.
4. User bi khoa khong the dang nhap.

**Ngoai le:** User khong ton tai hoac admin khong co quyen.

### UC-IAM-11 - Mo khoa tai khoan

**Tac nhan chinh:** Admin

**Tien dieu kien:** Da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin chon user bi khoa.
2. He thong kiem tra quyen admin.
3. He thong cap nhat status user thanh `ACTIVE`.

**Ngoai le:** User khong ton tai hoac admin khong co quyen.

### UC-IAM-12 - Nang nguoi dung thanh Owner

**Tac nhan chinh:** Admin/He thong

**Muc tieu:** Chuyen role user tu `CUSTOMER` sang `OWNER` de quan ly homestay.

**Luong chinh:**

1. He thong/Admin chon user can nang quyen.
2. He thong kiem tra user ton tai.
3. He thong chi cho phep user role `CUSTOMER` duoc promote.
4. He thong cap nhat role thanh `OWNER`.

**Ngoai le:** User khong ton tai hoac user khong phai `CUSTOMER`.

**Ghi chu:** Khi customer tao homestay, service co logic promote owner neu owner hien tai dang la `CUSTOMER`.

## 6. Nhom Catalog - Homestay, Phong, Tien Nghi, Danh Gia

### UC-CAT-01 - Xem danh sach homestay

**Tac nhan chinh:** Guest, Customer, Owner, Admin

**Luong chinh:**

1. Tac nhan mo danh sach homestay.
2. Tac nhan co the nhap tu khoa.
3. He thong tra danh sach homestay gom ten, dia chi, loai, trang thai.

**Hau dieu kien:** Khong thay doi du lieu.

### UC-CAT-02 - Xem chi tiet homestay

**Tac nhan chinh:** Guest, Customer, Owner, Admin

**Luong chinh:**

1. Tac nhan chon mot homestay.
2. He thong lay chi tiet homestay.
3. He thong hien thi ten, dia chi, loai, mo ta, anh, trang thai va danh sach phong lien quan.

**Ngoai le:** Homestay khong ton tai.

### UC-CAT-03 - Tao homestay

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Du lieu vao:** OwnerId, ten, dia chi, loai, mo ta.

**Luong chinh:**

1. Nguoi dung mo form tao homestay.
2. He thong kiem tra nguoi tao la chinh ownerId hoac admin.
3. He thong validate ten/dia chi/loai.
4. He thong kiem tra owner ton tai.
5. Neu owner dang la `CUSTOMER`, he thong promote thanh `OWNER`.
6. He thong tao homestay voi status mac dinh `ACTIVE`.

**Ngoai le:** Chua dang nhap, khong co quyen, owner khong ton tai, du lieu thieu.

### UC-CAT-04 - Quan ly homestay cua toi

**Tac nhan chinh:** Owner, Customer

**Tien dieu kien:** Da dang nhap.

**Luong chinh:**

1. Nguoi dung mo man hinh Homestay cua toi.
2. He thong lay danh sach homestay co `owner_id` bang user hien tai.
3. He thong hien thi danh sach de sua/xoa/quan ly phong.

**Ngoai le:** Chua dang nhap.

### UC-CAT-05 - Cap nhat homestay

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu homestay hoac admin.

**Du lieu vao:** Ten, dia chi, loai, mo ta, imageUrl, status.

**Luong chinh:**

1. Tac nhan chon homestay can sua.
2. He thong kiem tra quyen quan ly homestay.
3. Tac nhan nhap thong tin moi.
4. He thong validate du lieu.
5. He thong cap nhat homestay.

**Ngoai le:** Khong co quyen, homestay khong ton tai, du lieu khong hop le.

### UC-CAT-06 - Xoa homestay

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu homestay hoac admin.

**Luong chinh:**

1. Tac nhan chon homestay can xoa.
2. He thong kiem tra quyen quan ly homestay.
3. He thong xoa hoac danh dau homestay theo repository/domain.
4. He thong refresh danh sach.

**Ngoai le:** Khong co quyen hoac homestay khong ton tai.

### UC-CAT-07 - Xem danh sach phong

**Tac nhan chinh:** Guest, Customer, Owner, Admin

**Luong chinh:**

1. Tac nhan mo danh sach phong toan he thong hoac phong theo homestay.
2. Tac nhan co the tim kiem.
3. He thong tra danh sach phong gom homestayId, ten, loai, suc chua, trang thai.

### UC-CAT-08 - Xem chi tiet phong

**Tac nhan chinh:** Guest, Customer, Owner, Admin

**Luong chinh:**

1. Tac nhan chon phong.
2. He thong lay chi tiet phong va ten homestay.
3. He thong hien thi mo ta, anh, suc chua, trang thai, tien nghi, danh gia.

**Ngoai le:** Phong khong ton tai.

### UC-CAT-09 - Tao phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu homestay hoac admin.

**Du lieu vao:** HomestayId, ten phong, loai, suc chua, mo ta.

**Luong chinh:**

1. Tac nhan mo form tao phong.
2. He thong kiem tra quyen quan ly homestay.
3. He thong validate homestay ton tai, ten/loai khong trong, capacity > 0.
4. He thong tao phong voi status mac dinh `AVAILABLE`.

**Ngoai le:** Khong co quyen, homestay khong ton tai, capacity khong hop le.

### UC-CAT-10 - Cap nhat phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu phong hoac admin.

**Du lieu vao:** Ten, loai, suc chua, mo ta, imageUrl, status.

**Luong chinh:**

1. Tac nhan chon phong.
2. He thong kiem tra quyen quan ly phong.
3. Tac nhan cap nhat thong tin.
4. He thong validate capacity > 0 va text bat buoc.
5. He thong luu thong tin moi.

**Ngoai le:** Phong khong ton tai, khong co quyen, du lieu khong hop le.

### UC-CAT-11 - Xoa phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu phong hoac admin.

**Luong chinh:**

1. Tac nhan chon phong.
2. He thong kiem tra quyen quan ly phong.
3. He thong xoa phong.
4. He thong refresh danh sach.

**Ngoai le:** Phong khong ton tai hoac khong co quyen.

### UC-CAT-12 - Quan ly tien nghi

**Tac nhan chinh:** Admin

**Du lieu vao:** Ten tien nghi, mo ta.

**Luong chinh:**

1. Admin mo man hinh tien nghi.
2. Admin xem/tim danh sach tien nghi.
3. Admin co the tao tien nghi moi.
4. Admin co the cap nhat tien nghi.
5. Admin co the xoa tien nghi.

**Ngoai le:** Ten tien nghi trong, amenity khong ton tai.

**Ghi chu:** Lop `CatalogModule` khong ap dung check admin cho create/update/delete amenity, nhung UI dat chuc nang nay trong khu vuc Admin.

### UC-CAT-13 - Gan/Go tien nghi cho phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu phong hoac admin.

**Luong chinh:**

1. Tac nhan chon phong.
2. He thong kiem tra quyen quan ly phong.
3. Tac nhan chon tien nghi can gan hoac go.
4. He thong kiem tra phong va tien nghi ton tai.
5. He thong tao/xoa quan he trong `room_amenities`.

**Ngoai le:**

- Gan trung tien nghi: bao loi conflict.
- Phong/tien nghi khong ton tai hoac khong co quyen.

### UC-CAT-14 - Xem danh gia phong

**Tac nhan chinh:** Guest, Customer, Owner, Admin

**Luong chinh:**

1. Tac nhan mo chi tiet phong hoac man hinh danh gia.
2. He thong lay cac booking cua phong.
3. He thong lay danh gia gan voi cac booking do.
4. He thong hien thi rating, comment, ngay danh gia.

### UC-CAT-15 - Tao danh gia

**Tac nhan chinh:** Customer

**Tien dieu kien:** Customer da dang nhap; booking thuoc ve customer va da `COMPLETED`.

**Du lieu vao:** BookingId, rating, comment.

**Luong chinh:**

1. Customer mo booking da hoan tat.
2. He thong kiem tra booking co the danh gia.
3. Customer nhap rating va comment.
4. He thong validate rating tu 1 den 5.
5. He thong kiem tra booking status la `COMPLETED`.
6. He thong kiem tra booking chua co review.
7. He thong tao review.

**Ngoai le:** Booking chua completed, rating sai, booking da co review.

### UC-CAT-16 - Cap nhat danh gia

**Tac nhan chinh:** Customer/He thong

**Tien dieu kien:** Review da ton tai.

**Luong chinh:**

1. Tac nhan chon review can sua.
2. Tac nhan nhap rating/comment moi.
3. He thong validate rating tu 1 den 5.
4. He thong cap nhat review.

**Ngoai le:** Review khong ton tai hoac rating sai.

## 7. Nhom Inventory - Gia Va Lich Khoa

### UC-INV-01 - Tao lich gia phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu phong hoac admin.

**Du lieu vao:** RoomId, startDate, endDate, pricePerNight.

**Luong chinh:**

1. Tac nhan mo quan ly phong/gia.
2. He thong kiem tra quyen quan ly phong.
3. Tac nhan nhap khoang ngay va gia moi dem.
4. He thong validate phong ton tai, startDate <= endDate, pricePerNight > 0.
5. He thong tao pricing schedule.

### UC-INV-02 - Cap nhat lich gia phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Schedule ton tai; tac nhan co quyen quan ly phong cua schedule.

**Luong chinh:**

1. Tac nhan chon pricing schedule.
2. He thong xac dinh roomId cua schedule.
3. He thong kiem tra quyen quan ly phong.
4. Tac nhan cap nhat ngay/gia.
5. He thong validate va luu thay doi.

### UC-INV-03 - Xoa lich gia phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Schedule ton tai; tac nhan co quyen quan ly phong cua schedule.

**Luong chinh:**

1. Tac nhan chon pricing schedule.
2. He thong kiem tra quyen quan ly phong.
3. He thong xoa schedule.

### UC-INV-04 - Xem lich gia phong

**Tac nhan chinh:** Owner, Admin, He thong

**Luong chinh:**

1. Tac nhan/he thong truyen roomId.
2. He thong validate roomId.
3. He thong tra danh sach pricing schedule cua phong.

### UC-INV-05 - Tinh gia phong

**Tac nhan chinh:** He thong

**Kich hoat boi:** Tao booking.

**Luong chinh:**

1. He thong nhan roomId, checkInDate, checkOutDate.
2. He thong validate phong va khoang ngay.
3. He thong tinh so dem.
4. He thong lay gia theo pricing schedule cua phong.
5. He thong tra tong tien.

**Ngoai le:** Phong khong ton tai, khoang ngay sai, khong co/gia khong hop le tuy theo logic tinh gia.

### UC-INV-06 - Khoa ngay phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Da dang nhap; tac nhan la chu phong hoac admin.

**Du lieu vao:** RoomId, startDate, endDate, reason.

**Luong chinh:**

1. Tac nhan mo quan ly ngay khoa.
2. He thong kiem tra quyen quan ly phong.
3. Tac nhan nhap khoang ngay va ly do.
4. He thong validate startDate < endDate.
5. He thong kiem tra khoang ngay khong overlap voi blocked date hien co.
6. He thong tao blocked date.

**Ngoai le:** Khoang ngay sai, overlap, phong khong ton tai, khong co quyen.

### UC-INV-07 - Mo khoa ngay phong

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Blocked date ton tai; tac nhan co quyen quan ly phong.

**Luong chinh:**

1. Tac nhan chon blocked date.
2. He thong xac dinh roomId cua blocked date.
3. He thong kiem tra quyen quan ly phong.
4. He thong xoa blocked date.

### UC-INV-08 - Xem ngay bi khoa cua phong

**Tac nhan chinh:** Owner, Admin, He thong

**Luong chinh:**

1. Tac nhan/he thong truyen roomId.
2. He thong validate phong.
3. He thong tra danh sach ngay bi khoa va ly do.

## 8. Nhom Reservation - Booking

### UC-RES-01 - Tao booking

**Tac nhan chinh:** Customer, Admin

**Tien dieu kien:** Da dang nhap; phong ton tai; vi du so du neu tong tien > 0.

**Du lieu vao:** BookedBy, roomId, voucherCode, checkInDate, checkOutDate.

**Luong chinh:**

1. Customer/Admin chon phong va khoang ngay.
2. He thong kiem tra nguoi thao tac la chinh `bookedBy` hoac admin.
3. He thong kiem tra phong khong bi block trong khoang ngay.
4. He thong tinh gia phong va so dem.
5. He thong tinh voucher neu co.
6. He thong kiem tra phong khong co booking overlap.
7. He thong tao booking voi status `CONFIRMED`.
8. He thong tru tien vi bang tong thanh toan neu tong tien > 0.
9. He thong tao invoice va danh dau `PAID`.

**Ngoai le:**

- Chua dang nhap hoac khong co quyen.
- Phong bi khoa ngay.
- Phong da duoc booking trong khoang ngay.
- Voucher khong hop le/het han/het luot/chua dat gia tri toi thieu.
- So du vi khong du.
- Khoang ngay sai.

**Hau dieu kien:** Booking, invoice va wallet transaction duoc tao; so du customer thay doi.

### UC-RES-02 - Xem booking cua toi

**Tac nhan chinh:** Customer

**Tien dieu kien:** Da dang nhap.

**Luong chinh:**

1. Customer mo Booking cua toi.
2. He thong lay current user id.
3. He thong tra danh sach booking cua user gom phong, ngay, so dem, tong tien, status.

### UC-RES-03 - Xem chi tiet booking

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Quyen xem:** Admin, nguoi dat booking, hoac owner cua phong trong booking.

**Luong chinh:**

1. Tac nhan chon booking.
2. He thong lay chi tiet booking.
3. He thong kiem tra quyen xem.
4. He thong hien thi thong tin booking, voucher, tong tien, phi huy, tien hoan, status.

**Ngoai le:** Booking khong ton tai hoac khong co quyen.

### UC-RES-04 - Xem booking cua homestay toi

**Tac nhan chinh:** Owner

**Tien dieu kien:** Da dang nhap voi homestay/phong so huu.

**Luong chinh:**

1. Owner mo man hinh Booking.
2. He thong lay cac homestay cua owner.
3. He thong lay phong thuoc cac homestay do.
4. He thong lay booking cua cac phong.
5. He thong hien thi danh sach booking.

### UC-RES-05 - Xem truoc tien hoan khi huy

**Tac nhan chinh:** Customer

**Tien dieu kien:** Da dang nhap; booking thuoc customer.

**Luong chinh:**

1. Customer chon booking can huy.
2. He thong kiem tra booking thuoc customer.
3. He thong tinh chinh sach huy theo ngay hien tai.
4. He thong hien thi cancellationFee va refundAmount.

**Chinh sach huy:**

- Huy truoc check-in tu 7 ngay tro len: phi 0%, hoan 100%.
- Huy truoc check-in tu 3 den 6 ngay: phi 30%, hoan 70%.
- Huy truoc check-in tu 1 den 2 ngay: phi 50%, hoan 50%.
- Huy vao hoac sau ngay check-in: khong cho huy.

### UC-RES-06 - Huy booking

**Tac nhan chinh:** Customer

**Tien dieu kien:** Da dang nhap; booking thuoc customer; booking chua `CANCELLED`/`COMPLETED`.

**Luong chinh:**

1. Customer chon huy booking.
2. He thong kiem tra booking thuoc customer.
3. He thong tinh phi huy va tien hoan.
4. He thong cap nhat booking thanh `CANCELLED`, luu cancellationFee/refundAmount/cancelledAt.
5. He thong hoan tien vao vi neu refundAmount > 0.
6. He thong danh dau invoice la `REFUNDED`.

**Ngoai le:** Booking da huy/hoan tat, khong phai nguoi dat, huy vao/sau ngay check-in.

### UC-RES-07 - Hoan tat booking

**Tac nhan chinh:** Owner, Admin

**Tien dieu kien:** Booking dang `CONFIRMED`; tac nhan la owner cua phong hoac admin.

**Luong chinh:**

1. Owner/Admin chon booking can hoan tat.
2. He thong lay owner cua phong trong booking.
3. He thong kiem tra quyen.
4. He thong cap nhat booking thanh `COMPLETED`.
5. He thong cong doanh thu cho owner.
6. He thong cap nhat invoice settlement thanh `SETTLED`.

**Ngoai le:** Booking khong phai `CONFIRMED`, khong co quyen, booking khong ton tai.

### UC-RES-08 - Kiem tra quyen danh gia booking

**Tac nhan chinh:** Customer

**Tien dieu kien:** Da dang nhap.

**Luong chinh:**

1. He thong nhan bookingId.
2. He thong lay booking.
3. He thong kiem tra current user la nguoi dat booking.
4. He thong kiem tra booking `COMPLETED`.
5. He thong tra ve co/khong the danh gia.

## 9. Nhom Commerce - Voucher, Vi, Hoa Don

### UC-COM-01 - Tao voucher

**Tac nhan chinh:** Admin

**Tien dieu kien:** Da dang nhap voi role `ADMIN`.

**Du lieu vao:** CreatedBy, code, name, discountType, discountValue, maxDiscountAmount, startDate, endDate, maxUsage, minOrderValue.

**Luong chinh:**

1. Admin mo quan ly voucher.
2. Admin nhap thong tin voucher.
3. He thong kiem tra current user trung `createdBy` va co role admin.
4. He thong validate code/name, loai giam, gia tri giam, ngay, so luot, don toi thieu.
5. He thong kiem tra code khong trung.
6. He thong tao voucher status `ACTIVE`.

**Ngoai le:** Khong phai admin, code trung, discount invalid, ngay invalid.

### UC-COM-02 - Cap nhat voucher

**Tac nhan chinh:** Admin

**Tien dieu kien:** Voucher ton tai; da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin chon voucher.
2. Admin cap nhat thong tin.
3. He thong kiem tra quyen admin.
4. He thong validate va kiem tra code khong trung voucher khac.
5. He thong luu thay doi.

### UC-COM-03 - Vo hieu hoa voucher

**Tac nhan chinh:** Admin

**Tien dieu kien:** Voucher ton tai; da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin chon voucher.
2. He thong kiem tra quyen admin.
3. He thong cap nhat voucher thanh khong hoat dong.

### UC-COM-04 - Xem danh sach voucher

**Tac nhan chinh:** Admin

**Tien dieu kien:** Da dang nhap voi role `ADMIN`.

**Luong chinh:**

1. Admin mo quan ly voucher.
2. Admin co the tim kiem theo tu khoa.
3. He thong tra danh sach voucher va thong tin su dung.

### UC-COM-05 - Tinh giam gia voucher

**Tac nhan chinh:** He thong

**Kich hoat boi:** Tao booking hoac nguoi dung nhap voucher.

**Du lieu vao:** VoucherCode, originalAmount.

**Luong chinh:**

1. He thong nhan code voucher va tong tien goc.
2. He thong tim voucher theo code.
3. He thong kiem tra voucher `ACTIVE`, trong khoang ngay, con luot dung.
4. He thong kiem tra originalAmount >= minOrderValue.
5. He thong tinh discount theo `FIXED_AMOUNT` hoac `PERCENTAGE`.
6. Neu co maxDiscountAmount, he thong gioi han tien giam.
7. He thong tra discountAmount va finalAmount.

**Ngoai le:** Code trong/khong ton tai, voucher khong active/het han/het luot, don chua dat toi thieu.

### UC-COM-06 - Xem so du vi

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Luong chinh:**

1. Tac nhan mo man hinh Vi.
2. He thong lay current user id.
3. He thong hien thi balance.

### UC-COM-07 - Xem lich su giao dich vi

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Luong chinh:**

1. Tac nhan mo man hinh Vi.
2. He thong lay danh sach wallet transaction cua current user.
3. He thong hien thi loai giao dich, bookingId, so tien, so du truoc/sau, mo ta, ngay tao.

### UC-COM-08 - Nap tien vao vi

**Tac nhan chinh:** Customer, Owner, Admin

**Tien dieu kien:** Da dang nhap.

**Du lieu vao:** Amount.

**Luong chinh:**

1. Tac nhan mo dialog nap tien.
2. Tac nhan nhap so tien.
3. He thong validate amount > 0.
4. He thong cong tien vao balance.
5. He thong tao wallet transaction `DEPOSIT`.

**Ngoai le:** Amount <= 0.

### UC-COM-09 - Thanh toan booking bang vi

**Tac nhan chinh:** He thong

**Kich hoat boi:** UC-RES-01 Tao booking.

**Luong chinh:**

1. He thong nhan bookedBy, bookingId, subtotal, discountAmount, totalAmount.
2. He thong kiem tra bookedBy la current user hoac admin.
3. Neu totalAmount > 0, he thong kiem tra so du vi.
4. He thong tru totalAmount va tao transaction `PAYMENT`.
5. He thong tao invoice payment method `WALLET`.
6. He thong danh dau invoice `PAID`.

**Ngoai le:** So du khong du, totalAmount am, khong co quyen.

### UC-COM-10 - Hoan tien booking

**Tac nhan chinh:** He thong

**Kich hoat boi:** UC-RES-06 Huy booking.

**Luong chinh:**

1. He thong nhan bookedBy, bookingId, refundAmount.
2. He thong kiem tra bookedBy la current user hoac admin.
3. Neu refundAmount > 0, he thong cong tien vao vi va tao transaction `REFUND`.
4. He thong danh dau invoice `REFUNDED`.

**Ngoai le:** refundAmount am hoac khong co quyen.

### UC-COM-11 - Quyet toan doanh thu cho owner

**Tac nhan chinh:** Owner, Admin, He thong

**Kich hoat boi:** UC-RES-07 Hoan tat booking.

**Luong chinh:**

1. He thong nhan ownerId, bookingId, revenueAmount.
2. He thong kiem tra ownerId la current user hoac admin.
3. He thong cong revenueAmount vao vi owner.
4. He thong tao transaction `OWNER_REVENUE`.
5. He thong danh dau invoice settlement `SETTLED`.

**Ngoai le:** Khong co quyen, owner khong ton tai, amount khong hop le.

### UC-COM-12 - Xem hoa don theo booking

**Tac nhan chinh:** Admin, Customer, Owner

**Tien dieu kien:** Booking/invoice ton tai.

**Luong chinh:**

1. Tac nhan nhap/chon bookingId.
2. He thong tim invoice theo bookingId.
3. He thong hien thi subtotal, discountAmount, totalAmount, payment method/status/date, settlement status/date, refundedAt, createdAt.

**Ngoai le:** Khong tim thay invoice.

**Ghi chu:** `CommerceModule.getInvoiceByBooking` hien chua check phan quyen theo actor; UI admin co man hinh tra cuu hoa don, booking view cua customer/owner co the su dung thong tin invoice lien quan.

## 10. Luong Nghiep Vu Tich Hop Quan Trong

### 10.1 Customer dat phong co voucher

1. Customer dang nhap.
2. Customer xem homestay/phong.
3. Customer chon phong, check-in/check-out va voucher code.
4. Reservation kiem tra phong khong bi khoa.
5. Inventory tinh gia phong.
6. Commerce tinh voucher.
7. Reservation kiem tra booking khong overlap va tao booking `CONFIRMED`.
8. Commerce tru tien vi va tao transaction `PAYMENT`.
9. Billing sinh invoice `PAID`.

### 10.2 Customer huy booking

1. Customer xem Booking cua toi.
2. Customer xem truoc tien hoan.
3. Customer xac nhan huy.
4. Reservation cap nhat booking `CANCELLED`.
5. Commerce hoan tien theo chinh sach.
6. Billing cap nhat invoice `REFUNDED`.

### 10.3 Owner hoan tat booking va nhan doanh thu

1. Owner xem booking cua cac phong minh so huu.
2. Owner chon booking `CONFIRMED`.
3. Reservation cap nhat booking `COMPLETED`.
4. Commerce cong doanh thu cho vi owner.
5. Billing cap nhat settlement `SETTLED`.
6. Customer co the tao review cho booking da completed.

### 10.4 Customer tao homestay lan dau

1. Customer dang nhap.
2. Customer tao homestay.
3. Catalog kiem tra owner la current user.
4. HomestayService thay owner dang role `CUSTOMER`.
5. He thong promote user thanh `OWNER`.
6. Homestay moi duoc tao va user co the quan ly phong/homestay.

## 11. Ma Nguon Doi Chieu

| Nhom | File chinh |
| --- | --- |
| IAM API | `src/com/homestaybooking/shared/interfaces/IIamModule.java` |
| Catalog API | `src/com/homestaybooking/shared/interfaces/ICatalogModule.java` |
| Inventory API | `src/com/homestaybooking/shared/interfaces/IInventoryModule.java` |
| Reservation API | `src/com/homestaybooking/shared/interfaces/IReservationModule.java` |
| Commerce API | `src/com/homestaybooking/shared/interfaces/ICommerceModule.java` |
| Dieu huong UI | `src/com/homestaybooking/HomeView.java` |
| Database schema | `database/migrate.sql` |
| Workflow tests | `test/com/homestaybooking/features/FeatureWorkflowTest.java` |
