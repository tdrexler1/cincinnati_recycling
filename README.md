# Cincinnati Recycling Data

Using Spark to query a large dataset of recycling cart tip data from the Cincinnati Open Data Portal.

This was a class project for "Big Data: High-Performance Computing."

## Overview

## Data Sources
The primary data set I used was [“Recycle Carts Collection ‘Tip’ Data”](https://data.cincinnati-oh.gov/Thriving-Neighborhoods/Recycle-Carts-Collection-Tip-Data/3kem-bs7v) available from the [City of Cincinnati Open Data Portal](https://data.cincinnati-oh.gov/).

For Question 4, I also collected data from the [US Census Bureau](https://data.census.gov/). The data I used came from detailed tables of the 2018 American Community Survey 5-year estimates.  The table numbers and titles were:

*	B15003 - EDUCATIONAL ATTAINMENT FOR THE POPULATION 25 YEARS AND OVER
*	B19001 - HOUSEHOLD INCOME IN THE PAST 12 MONTHS (IN 2018 INFLATION-ADJUSTED DOLLARS)
*	B17021 - POVERTY STATUS OF INDIVIDUALS IN THE PAST 12 MONTHS BY LIVING ARRANGEMENT

I downloaded data at the census block group level to match the block groups in each of the [Cincinnati Statistical Neighborhood Approximations](https://www.cincinnati-oh.gov/planning/maps-and-data/census-demographics/2010-census/) (SNAs) as shown in the maps available from the Cincinnati Department of City Planning. The names of the SNAs coincide with the values of the “NEIGHBORHOOD” column in the recyling data. The repository includes a [copy of a csv file](CIN_2010_CT_BG_flat.csv) listing the census tracts (CT column) and Block Groups (BG column) in each SNA; I used this file to join the two data sets.

## Research Questions and Results

Questions 1 & 2 examine the rate of participation in the Cincinnati recycling program, first by neighborhood (Question 1) and then by city block (Question 2).  According to the data set description, “the Cartlift field indicates how many time (sic) the recycling cart was ‘tipped’ in a given month.”  I chose to define recycling program “participants” as an address with at least one cart tip recorded in a month. Dividing the number of participating addresses by the total number of addresses in the neighborhood or city block produced monthly participation rates.  This does not account for rates of participation within a single address, such as multi-unit buildings or single-unit buildings associated with multiple bin RFID numbers.

### Question 1

Which neighborhoods have average monthly recycling program participation rates in the bottom 10 percent of all neighborhoods? Consider residential addresses only.

**Results:** 

| Neighborhood | Average Monthly Recycling<br>Program Participation Rate |
| --- | ---: |
|ENGLISH WOODS | 0.05657894736842104 |
|WINTON HILLS | 0.0835981531258129 |
|VILLAGES AT ROLL HILL | 0.15105042016806725 |
|SOUTH FAIRMOUNT | 0.18403062580694163 |
|MILLVALE | 0.1872814270402695 |

**Motivation:**

The neighborhoods with the lowest average monthly participation rates might benefit from targeted outreach designed to increase program participation.  Though this question is limited in scope, the full set of results including all neighborhoods also gives an overview of how participation rates vary throughout the city. 

### Question 2

What are the top 10 city blocks measured by average monthly recycling program participation rates?  For the purposes of this question, a city block is defined as addresses on the same street having numbers within the same “hundreds” place, i.e., 301, 359, 378, would be in one block, 4502, 4523, 4567, would be in a different block.  Consider residential addresses only and exclude any block with fewer than 10 addresses.

**Results:**

| Block (Number of Addresses) | Average Monthly Recycling<br>Program Participation Rate | Rank |
| --- | ---: | ---: |
| 700 WAKEFIELD DR (15) | 0.9749999999999999 | 1
| 800 WAKEFIELD DR (17) | 0.9647058823529411 | 2
| 100 LAFAYETTE CIR (24) | 0.9541666666666668 | 3
| 1200 DEAN CT (13) | 0.9365384615384615 | 4
| 6200 ROBISON RD (25) | 0.9360000000000005 | 5
| 1200 CLIFF LAINE DR (12) | 0.9354166666666666 | 6
| 1400 OAK KNOLL DR (16) | 0.934375 | 7
| 6300 KINCAID RD (13) | 0.9211538461538462 | 8
| 2100 BUDWOOD CT (12) | 0.9166666666666666 | 9
| 6300 PARKMAN PL (12) | 0.9125 | 10

**Motivation:**

As in question 1, the results included here are filtered from a larger result set which might have other uses.  For example, instead of comparing participation rates of blocks city-wide, they could be compared to other blocks on the same street or in the same neighborhood. This information could be used in a positive way to encourage neighbors to determine which block can achieve the highest average monthly participation rate, leveraging civic pride to increase overall participation in the recycling program.

### Question 3

List recycling truck routes with a monthly average of cart tips greater than one standard deviation above the monthly average of cart tips calculated for all routes.  Include residential and commercial addresses.  Exclude routes labelled “UP” (which appear to be low-volume, supplemental routes).  

**Results:**

Monthly average cart tips, calculated for all routes: 580.0044

Standard deviation of individual route monthly average cart tips: 314.3280659183882

|Route Name | Average Monthly Cart Tips |
| --- | ---: |
| TUE-U2-GOLD | 1197.4000 |
| WED-U8-GREEN | 1098.6000 |
| WED-U3-GREEN | 1086.9250 |
| TUE-U8-GOLD | 1018.3000 |
| WED-U2-GREEN | 986.6750 |
| MON-U2-GREEN | 985.6250 |
| THU-U8-GOLD | 949.2000 |
| TUE-U12-GOLD | 896.9750 |

**Motivation:**

The results of this question should identify recycling truck routes with the heaviest pick-up volume, which could be useful in further analysis of route efficiency, the expected need for supplemental routes, and the possibility of route splitting. This depends on the extent to which the number of cart tips corresponds to the volume of materials.  For example, the carts in the data set vary in capacity and it is likely the volume of material within each cart varies from week to week.  However, it seems reasonable to assume that the two measurements are somewhat correlated and tip counts could be used as rough proxy measure of pick-up volume. 

### Question 4

At the neighborhood level, determine whether there is evidence of a relationship between participation in the recycling program and each of the demographic characteristics of educational attainment, household annual income, and incidence of poverty.  Consider residential addresses only.

**Results:**

| Variable | Pearson’s Correlation Coefficient |
| --- | ---: |
| Education: up to high school | -0.6639075002188944 |
| Education: up to bachelor’s degree | 0.5429677590764169 |
| Education: above bachelor’s degree | 0.6432057991540835 |
| Income: below $25,000 | -0.7693372574989855 |
| Income: between $25,000 and $39,000 | 0.12409668632848933 |
| Income: between $40,000 and $75,000 | 0.41172711015737484 |
| Income: above $75,000 | 0.6696463186102165 |
| Poverty: below poverty level | -0.7591604703412604 |
| Poverty: at or above poverty level | 0.7591604703412602 |

**Motivation:**

This question measured the linear correlation between the neighborhood average monthly recycling program participation rates calculated in question 1 and neighborhood-level demographic data from the US Census Bureau.  The Census data required some additional processing before correlation could be calculated: first, the categories in both the educational attainment and household income topics were condensed; then, along with the poverty topic, the data were converted to percentages of individuals/households in each category.

The results show a strong negative correlation between recycling participation rates and: 1) the percentage of individuals 25 or older in a neighborhood who have completed a high school education or less; 2) the percentage of individuals in a neighborhood earning less than $25,000 annually; and 3) the percentage of households in a neighborhood earning income below the poverty level.  Similarly, there is some evidence of positive correlation between recycling participation rates and: 1) the percentage of individuals 25 or older in a neighborhood who have completed a master’s degree or higher; 2) the percentage of individuals in a neighborhood earning more than $75,000 annually; and 3) the percentage of households in a neighborhood earning income above the poverty level.  

Educational attainment, household income, and poverty status are related to one another (directly in the case of income and poverty status), so these variables alone would not produce a useful regression model to predict neighborhood recycling participation rates.  However, the correlations seen in the results underscore the potential challenge of promoting participation in the recycling program: residents in neighborhoods with low participation rates likely have more immediate financial and social problems to address.
