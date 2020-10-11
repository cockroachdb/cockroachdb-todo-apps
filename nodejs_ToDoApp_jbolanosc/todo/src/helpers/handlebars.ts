import moment from "moment";

export const formatDate = (date: Date) => {
  return moment(date).fromNow();
};
