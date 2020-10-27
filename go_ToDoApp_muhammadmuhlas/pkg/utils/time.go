package utils

import (
	"fmt"
	"time"
)

func PastFormat(t time.Time) string {
	now := time.Now()
	if now.Add(-60 * time.Second).Before(t) {
		return "a few seconds ago"
	}
	if now.Add(-5 * time.Minute).Before(t) {
		return "a few minutes ago"
	}
	diff := now.Sub(t)
	if diff.Minutes() < 60 {
		return fmt.Sprintf("%2.f minutes ago", diff.Minutes())
	}
	if diff.Hours() == 1 {
		return "an hour ago"
	}
	if diff.Hours() < 24 {
		return fmt.Sprintf("%2.f hours ago", diff.Hours())
	}
	if diff.Hours() < 168 {
		return fmt.Sprintf("%2.f days ago", diff.Hours()/24)
	}
	return "a long time ago"
}
