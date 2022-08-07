# Supplementary Specification (FURPS+)

## Functionality

_Specifies functionalities that:_

- _are common across several US/UC;_
- _are not related to US/UC, namely: Audit, Reporting and Security._



- _Security : “All those who wish to use the application must be authenticated.”_

- _Email: “The client receives the notification by SMS and e-mail.”_

- _Help: “the application user manual (in the annexes) that must be delivered with the application.”_

- _Reporting:” The company is also required to generate daily (automatic) reports with all the information demanded by the NHS and should send them to the NHS using their API._

- _Workflow: “the application must allow ordering the clients by TIN and by name.”_



## Usability 

_Evaluates the user interface. It has several subcategories,
among them: error prevention; interface aesthetics and design; help and
documentation; consistency and standards._
- _Interface aesthetics and design_:
  >"The user interface must be simple, intuitive and consistent."


## Reliability
_Refers to the integrity, compliance and interoperability of the software. The requirements to be considered are: frequency and severity of failure, possibility of recovery, possibility of prediction, accuracy, average time between failures._
- _Frequency and Severity of failure_:
  > "The system should not fail more than 5 days in one year. Whenever the system fails, there should be no data loss."

## Performance
_Evaluates the performance requirements of the software, namely: response time, start-up time, recovery time, memory consumption, CPU usage, load capacity and application availability._
- _Response Time_:
  >"Any interface between a user and the system shall have a maximum response time of 3 seconds."
- _Start-up Time_:
  > "The system should start up in less than 10 seconds."

## Supportability
_The supportability requirements gathers several characteristics, such as:
testability, adaptability, maintainability, compatibility,
configurability, installability, scalability and more._ 

- _Configurability_: 
  >“The ordering algorithm (…) must be defined through a configuration file”.
  >“The algorithm to be used by the application must be defined through a configuration file”
- _Testability:_
  >“The development team must implement unit tests for all methods except methods that implement Input/Output operations.” 
- _Scalability:_
  >“the system should be developed having in mind the need to easily support other kinds of tests”
- _Compatibility:_
  >"The application should run on all platforms for which there exists a Java Virtual Machine."

## +

### Design Constraints

_Specifies or constraints the system design process. Examples may include: programming languages, software process, mandatory standards/patterns, use of development tools, class library, etc._


- programming languages: " The application must be developed in Java language”
  >“Graphical interface is to be developed in JavaFx11”
    - mandatory standards/patterns:
      > “Adopt recognized coding standards (e.g., CamelCase)”


### Implementation Constraints

_Specifies or constraints the code or construction of a system such
such as: mandatory standards/patterns, implementation languages,
database integrity, resource limits, operating system._


- implementation languages: 
  >“The application must be developed in Java language”
- mandatory standards/patterns:
  >“Adopt recognized coding standards (e.g., CamelCase)”


### Interface Constraints
_Specifies or constraints the features inherent to the interaction of the
system being developed with other external systems._

>“When sampling (blood or swab) the medical lab technician records the samples in the system, associating the samples with the client/test, and identifying each sample with a barcode that is automatically generated using an external API.”

>“The company is also required to generate daily (automatic) reports with all the information demanded by the NHS and should send them to the NHS using their API.”

### Physical Constraints

_Specifies a limitation or physical requirement regarding the hardware used to house the system, as for example: material, shape, size or weight._

- _Ram:_
  >"The application will be deployed to a machine with 8GB of RAM."