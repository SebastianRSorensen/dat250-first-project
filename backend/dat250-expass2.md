Report on API Setup and Issues Encountered
Introduction:
During the development of the polling and voting API, several challenges arose related to data handling, serialization, and testing. These issues were primarily related to how entities were structured and how requests and responses were processed.

Key Issues Encountered:
Serialization/Deserialization with Jackson:

Problem: Faced multiple Jackson deserialization issues, especially with Poll, User, and Vote objects. The main issues were infinite recursion and incorrect serialization.
Resolution: Used @JsonIdentityInfo and @JsonBackReference to prevent recursion and handle entity relationships.
Circular Reference Handling:

Problem: Circular references between entities like Poll and User caused serialization problems.
Resolution: Applied @JsonIdentityReference to serialize only relevant parts of entities (e.g., using username instead of full User object).
HTTP Response Errors:

Problem: Encountered 400 Bad Request and 500 Internal Server Error due to incorrect or missing request data.
Resolution: Ensured proper validation and structure of request bodies for user and poll creation.
Integration Testing with RestTemplate:

Problem: Integration tests failed due to incorrect object serialization during HTTP requests.
Resolution: Ensured that DTOs like VoteRequest and PollRequest were structured correctly and tests matched the API expectations.
Vote Change and Poll Deletion:

Problem: Errors occurred when users changed votes or tried to delete a poll.
Resolution: Created a delete poll endpoint that checks if the user deleting the poll is its creator, sending the username in the request body.
Null Pointer Exceptions:

Problem: NullPointerExceptions occurred when collections like Set<VoteOption> were not initialized.
Resolution: Initialized collections during object creation to avoid these errors.
Conclusion:
The main challenges involved serialization, circular references, and testing. By using proper annotations and ensuring consistent data handling, the API now functions as intended, with all key features implemented and tested successfully.
