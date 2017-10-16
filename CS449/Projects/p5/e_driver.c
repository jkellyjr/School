/**************************************
  Project 5: e Driver
  Modifications by: John Kelly Jr (jjk79)
  Original Author: Valerie Henson <val@nmt.edu>
  Minimal kernel module - /dev version
**************************************/
#include <linux/fs.h>
#include <linux/init.h>
#include <linux/miscdevice.h>
#include <linux/module.h>
#include <asm/uaccess.h>
#include <linux/slab.h>


char DIGITS[50000];

//This is a spigot algorithm for generating the digits of e without
//floating point math.
/****************************
	m + 1 is the max digits
	buffer contains digits
****************************/
void e(char *buffer, int m)
{
	int i, j, q;
	int *A;
	A = kmalloc(m * sizeof(int), __GFP_WAIT | __GFP_IO | __GFP_FS);
	buffer[0] = '2';
	int next = 1;

	for ( j = 0; j < m; j++ )
		A[j] = 1;

	for ( i = 0; i < m - 2; i++ ) {
		q = 0;
		for ( j = m - 1; j >= 0; ) {
			A[j] = 10 * A[j] + q;
			q = A[j] / (j + 2);
			A[j] %= (j + 2);
			j--;
		}
		buffer[next++] = (q + '0');
	}
	kfree(A);
}


/*
 * e_read is the function called when a process calls read() on
 * /dev/e_digits.  It writes the specified digits to the buffer passed in the
 * read() call.
 */

static ssize_t e_read(struct file * file, char * buf,
			  size_t count, loff_t *ppos)
{

	char display[50000];
	int min, max;

	// asignment for better control
	min = (int) *ppos;
	max = (int) count;

	// addressable index check
	if (count < 0){
		return -EINVAL;
	}
	// prior opertaions check
	if (*ppos != 0){
		return 0;
	}

	// generate the digits
	 e(DIGITS, max);

	// send digit to then user buffer
	if (copy_to_user(buf, DIGITS, max)){ return -EINVAL; }


	*ppos = -1;

	return max;
}

/*
 * The only file operation we care about is read.
 */
// !! file operations struct defines file operations when accessed
static const struct file_operations e_digits_fops = {
	.owner		= THIS_MODULE,
	.read		= e_read,
};

// !! struct containing info to register a misc device in kernel
static struct miscdevice e_digits_dev = {
	/*
	 * We don't care what minor number we end up with, so tell the
	 * kernel to just pick one.
	 */
	MISC_DYNAMIC_MINOR,
	/*
	 * Name ourselves /dev/e_digits.
	 */
	"e_driver",
	/*
	 * What functions to call when a program performs file
	 * operations on the device.
	 */
	&e_digits_fops
};

// !! registers a misc device in kernel
static int __init
e_digits_init(void)
{
	int ret;

	/*
	 * Create the "e_digits" device in the /sys/class/misc directory.
	 * Udev will automatically create the /dev/e_digits device using
	 * the default rules.
	 */
	ret = misc_register(&e_digits_dev);
	if (ret)
		printk(KERN_ERR
		       "Unable to register e_digits misc device\n");

	return ret;
}

module_init(e_digits_init);

// !! unregisters a misc device in kernel
static void __exit
e_digits_exit(void)
{
	misc_deregister(&e_digits_dev);
}

module_exit(e_digits_exit);

MODULE_LICENSE("GPL");
MODULE_AUTHOR("Valerie Henson <val@nmt.edu>");
MODULE_DESCRIPTION("\"e_digits\" minimal module");
MODULE_VERSION("dev");

