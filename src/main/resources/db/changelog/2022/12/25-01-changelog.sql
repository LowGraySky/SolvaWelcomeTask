-- liquibase formatted sql

-- changeset lowgraysky:1671988741822-1
CREATE SEQUENCE IF NOT EXISTS public.hibernate_sequence START WITH 1 INCREMENT BY 1;

-- changeset lowgraysky:1671988741822-2
CREATE TABLE public.BANK_ACCOUNT
(
    ID      BIGINT       NOT NULL,
    ADDRESS BIGINT       NOT NULL,
    TYPE    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_bank_account PRIMARY KEY (ID)
);

-- changeset lowgraysky:1671988741822-3
CREATE TABLE public.CURRENCY
(
    ID         BIGINT     NOT NULL,
    SHORT_NAME VARCHAR(3) NOT NULL,
    CONSTRAINT pk_currency PRIMARY KEY (ID)
);

-- changeset lowgraysky:1671988741822-4
CREATE TABLE public.EXCHANGE_RATE
(
    ID         BIGINT       NOT NULL,
    SYMBOL     VARCHAR(255) NOT NULL,
    OPEN       DECIMAL,
    CLOSE      DECIMAL,
    HIGH       DECIMAL,
    LOW        DECIMAL,
    "dateTime" date,
    CONSTRAINT pk_exchange_rate PRIMARY KEY (ID)
);

-- changeset lowgraysky:1671988741822-5
CREATE TABLE public.TRANSACTION
(
    ID                   BIGINT                      NOT NULL,
    BANK_ACCOUNT_FROM_ID BIGINT                      NOT NULL,
    BANK_ACCOUNT_TO_ID   BIGINT                      NOT NULL,
    CURRENCY_ID          BIGINT                      NOT NULL,
    SUM                  DECIMAL                     NOT NULL,
    EXPENSE_CATEGORY     VARCHAR(255)                NOT NULL,
    DATE_TIME            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    LIMIT_EXCEDEED       BOOLEAN                     NOT NULL,
    TRANSACTION_LIMIT_ID BIGINT,
    CONSTRAINT pk_transaction PRIMARY KEY (ID)
);

-- changeset lowgraysky:1671988741822-6
CREATE TABLE public.TRANSACTION_LIMIT
(
    ID               BIGINT                      NOT NULL,
    AMOUNT           DECIMAL                     NOT NULL,
    EXPENSE_CATEGORY VARCHAR(255)                NOT NULL,
    STAND_BY         TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CURRENCY_ID      BIGINT                      NOT NULL,
    AVAILABLE_AMOUNT DECIMAL                     NOT NULL,
    BANK_ACCOUNT_ID  BIGINT                      NOT NULL,
    MONTH            INTEGER                     NOT NULL,
    CONSTRAINT pk_transaction_limit PRIMARY KEY (ID)
);

-- changeset lowgraysky:1671988741822-7
ALTER TABLE public.BANK_ACCOUNT
    ADD CONSTRAINT uc_bank_account_address UNIQUE (ADDRESS);

-- changeset lowgraysky:1671988741822-8
ALTER TABLE public.CURRENCY
    ADD CONSTRAINT uc_currency_short_name UNIQUE (SHORT_NAME);

-- changeset lowgraysky:1671988741822-9
ALTER TABLE public.TRANSACTION_LIMIT
    ADD CONSTRAINT uc_transaction_limit_stand_by UNIQUE (STAND_BY);

-- changeset lowgraysky:1671988741822-10
ALTER TABLE public.TRANSACTION_LIMIT
    ADD CONSTRAINT FK_TRANSACTION_LIMIT_ON_BANK_ACCOUNT FOREIGN KEY (BANK_ACCOUNT_ID) REFERENCES public.BANK_ACCOUNT (ID);

-- changeset lowgraysky:1671988741822-11
ALTER TABLE public.TRANSACTION_LIMIT
    ADD CONSTRAINT FK_TRANSACTION_LIMIT_ON_CURRENCY FOREIGN KEY (CURRENCY_ID) REFERENCES public.CURRENCY (ID);

-- changeset lowgraysky:1671988741822-12
ALTER TABLE public.TRANSACTION
    ADD CONSTRAINT FK_TRANSACTION_ON_BANK_ACCOUNT_FROM FOREIGN KEY (BANK_ACCOUNT_FROM_ID) REFERENCES public.BANK_ACCOUNT (ID);

-- changeset lowgraysky:1671988741822-13
ALTER TABLE public.TRANSACTION
    ADD CONSTRAINT FK_TRANSACTION_ON_BANK_ACCOUNT_TO FOREIGN KEY (BANK_ACCOUNT_TO_ID) REFERENCES public.BANK_ACCOUNT (ID);

-- changeset lowgraysky:1671988741822-14
ALTER TABLE public.TRANSACTION
    ADD CONSTRAINT FK_TRANSACTION_ON_CURRENCY FOREIGN KEY (CURRENCY_ID) REFERENCES public.CURRENCY (ID);

-- changeset lowgraysky:1671988741822-15
ALTER TABLE public.TRANSACTION
    ADD CONSTRAINT FK_TRANSACTION_ON_TRANSACTION_LIMIT FOREIGN KEY (TRANSACTION_LIMIT_ID) REFERENCES public.TRANSACTION_LIMIT (ID);
