package terminal

import (
	"fmt"

	"github.com/alexeyco/simpletable"
	"strconv"
	"todo_app/pkg/model"
	"todo_app/pkg/utils"
)

const (
	ColorDefault = "\x1b[39m"

	ColorRed   = "\x1b[91m"
	ColorGreen = "\x1b[32m"
	ColorBlue  = "\x1b[94m"
	ColorGray  = "\x1b[90m"
)

func DisplayTasks(data []model.Task) {
	table := simpletable.New()
	var completed float64

	table.Header = &simpletable.Header{
		Cells: []*simpletable.Cell{
			{Align: simpletable.AlignLeft, Text: "ID"},
			{Align: simpletable.AlignLeft, Text: "Name"},
			{Align: simpletable.AlignCenter, Text: "Status"},
			{Align: simpletable.AlignCenter, Text: "Created At"},
			{Align: simpletable.AlignCenter, Text: "Updated At"},
		},
	}
	var tmp [][]*simpletable.Cell
	for _, v := range data {
		tmp = append(tmp, []*simpletable.Cell{
			{Text: blue(strconv.Itoa(int(v.ID)))},
			{Text: v.Name},
			{Text: func() string {
				if v.Checked {
					completed += 1
					return green("Finished")
				}
				return red("On Going")
			}(), Align: simpletable.AlignCenter},
			{Text: gray(utils.PastFormat(v.CreatedAt)), Align: simpletable.AlignCenter},
			{Text: blue(utils.PastFormat(v.UpdatedAt)), Align: simpletable.AlignCenter},
		})
	}
	table.Body = &simpletable.Body{
		Cells: tmp,
	}

	table.Footer = &simpletable.Footer{
		Cells: []*simpletable.Cell{
			{Align: simpletable.AlignCenter, Span: 5, Text: green(fmt.Sprintf("%d of %d Tasks Completed (%.2f%%)", int(completed), len(data), completed/float64(len(data))*100))},
		},
	}

	table.Println()
}

func red(s string) string {
	return fmt.Sprintf("%s%s%s", ColorRed, s, ColorDefault)
}

func green(s string) string {
	return fmt.Sprintf("%s%s%s", ColorGreen, s, ColorDefault)
}

func blue(s string) string {
	return fmt.Sprintf("%s%s%s", ColorBlue, s, ColorDefault)
}

func gray(s string) string {
	return fmt.Sprintf("%s%s%s", ColorGray, s, ColorDefault)
}
