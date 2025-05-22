from faker import Faker
import csv
from datetime import date

fake = Faker()
fake.unique.clear()

CSV_PATH = "dummy_data.csv"

with open(CSV_PATH, "w", newline="", encoding="utf-8") as csvfile:
    writer = csv.writer(csvfile)
    writer.writerow([
        "id",
        "first_name",
        "last_name",
        "phone",
        "ssn",
        "address",
        "birthdate",
        "email",
        "date_joined"
    ])

    for _ in range(100):
        pk_id = fake.uuid4()
        first = fake.unique.first_name()
        last  = fake.unique.last_name()
        phone = fake.phone_number()
        ssn   = fake.ssn()
        addr  = fake.address().replace("\n", ", ")
        birth = fake.date_of_birth(minimum_age=18, maximum_age=90).isoformat()

        # tie email to the name:
        domain = fake.free_email_domain()
        local  = f"{first}.{last}".lower().replace(" ", "")
        email  = f"{local}@{domain}"

        join = fake.date_between_dates(
            date_start=date(2023, 1, 1),
            date_end=date.today()
        ).isoformat()

        writer.writerow([
            pk_id, first, last,
            phone, ssn, addr,
            birth, email, join
        ])

print(f" Wrote 100 records to {CSV_PATH}")
