# README.txt
#
# Copyright (C) 2012-2024 Rafael Corchuelo.
#
# In keeping with the traditional purpose of furthering education and research, it is
# the policy of the copyright owner to permit non-commercial use and redistribution of
# this software. It has been tested carefully, but it is not guaranteed for any particular
# purposes.  The copyright owner does not offer any warranties or representations, nor do
# they accept any liabilities with respect to them.

Acme Software Factory, Inc. (Acme SF, Inc. for short) is a fictitious company that specialises
 in helping organisations get started on a variety of topics related to the management and 
 development of software projects. The goal of this project is to develop a WIS to help this 
 organisation manage their business, from projects to user stories, contracts, progress logs, 
 and quality audits. 

To get this project up and running, please follow the guideline in the theory/lab materials,
taking into account that you must link the appropriate version of the Acme-Framework excluding 
the following resources:

- **/fragments/**


Project Considerations and Hypothetical Offset Period Notes

- Publish Feature Issue: 
  - The initial implementation of the publish feature in the JSP did not account for the ID, potentially causing a panic when accessing other features. To address this, we decided that if an error occurs during the publish form submission, the only available options would be to either return or resubmit the publish. This decision was made after further analyzing the repercussions and impact following the implementation of the test cases.

- Impact on Testing Due to Sample Data Inconsistencies:
  - During the testing phase, some inconsistencies were discovered in the sample data after defining the test suite. Changing this data would cause all tests to fail. To resolve this, we created a new sample data set (sample data-01) where the affected entities (Training Session, Sponsorship, and Invoices) are corrected, thus meeting the complete sample data requirements.

- .gitignore Issues and Test Report Inconsistencies:
  - Due to issues with the `.gitignore` file, some test cases for the manager had to be redone. This might cause inconsistencies when attempting to recreate the testing performance. To address this, we have included a CSV file with the previous test cases for reference and any further analysis required for corrections.

- Link and Email Validation:
  - The lower interval for link and email validity is set to the default (0) since the validity is established by their notation. The only affected incomplete URL would be "ftp://", but this is an isolated case, as "http://" is also considered invalid and would be acceptable. Any further changes to these limitations would be made during the offset period.

- Invoices Hacking Test Cases:
  - Hacking test cases for Invoices generate a 404 error instead of a 500 error. Despite this, the integrity and security of this section function as expected.

- Error null respones for non-feature id requests in banner:
  - During the replay of banner, some random feature may obtain an error message specifying that a request is null. After futher analysis and checking in the csv, the "random null" is associated to images, css or random libraries not related to the banner feature.
